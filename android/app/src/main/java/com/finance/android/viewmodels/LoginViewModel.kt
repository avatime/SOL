package com.finance.android.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.android.datastore.UserStore
import com.finance.android.domain.dto.request.CheckUserRequestDto
import com.finance.android.domain.dto.request.LoginRequestDto
import com.finance.android.domain.dto.request.ReLoginRequestDto
import com.finance.android.domain.dto.request.SignupRequestDto
import com.finance.android.domain.dto.response.LoginResponseDto
import com.finance.android.domain.repository.UserRepository
import com.finance.android.ui.fragments.SignupStep
import com.finance.android.ui.screens.login.InputUserInfoStep
import com.finance.android.utils.Response
import com.finance.android.utils.validateBirthday
import com.finance.android.utils.validatePhoneNum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val name = mutableStateOf("")
    val birthday = mutableStateOf("")
    val phoneNumber = mutableStateOf("")

    val code = mutableStateOf("")
    private val rightCode = mutableStateOf<Response<String>>(Response.Loading)

    val password = mutableStateOf("")
    val passwordRepeat = mutableStateOf<String?>(null)

    fun isPossibleGoNext(
        signupStep: SignupStep,
        step: InputUserInfoStep? = null
    ): Boolean {
        return when (signupStep) {
            SignupStep.InputUserInfo -> when (step!!) {
                InputUserInfoStep.NAME -> name.value.isNotEmpty()
                InputUserInfoStep.BIRTHDAY -> validateBirthday(birthday.value)
                InputUserInfoStep.PHONE_NUM -> validatePhoneNum(phoneNumber.value)
            }
            SignupStep.TestPhone     -> code.value.isNotEmpty() && rightCode.value is Response.Success<String> && (rightCode.value as Response.Success<String>).data.equals(code.value)
            SignupStep.InputPassword -> {
                if (passwordRepeat.value == null) {
                    password.value.length == 6
                } else {
                    password.value == passwordRepeat.value
                }
            }
            SignupStep.Done          -> true
        }
    }

    fun loadRightCode() {
        viewModelScope.launch {
            userRepository.loadPhoneCode()
                .collect {
                    rightCode.value = it
                }
        }
    }

    fun showRightCode(): String? {
        if (rightCode.value !is Response.Success<String>) {
            return null
        }
        return (rightCode.value as Response.Success<String>).data
    }

    fun checkUser(
        onMoveSignupScreen: () -> Unit,
        onUsedPhoneNumber: () -> Unit,
        onMoveLoginScreen: () -> Unit
    ) {
        viewModelScope.launch {
            val birth = birthday.value.let {
                val result = "${it.substring(0..1)}-${it.substring(2..3)}-${it.substring(4..5)}"
                return@let if (it.substring(0..1)
                    .toInt() < 10
                ) {
                    "20$result"
                } else {
                    "19$result"
                }
            }
            val checkUserRequestDto = CheckUserRequestDto(
                userName = name.value,
                phoneNumber = phoneNumber.value,
                birthday = birth
            )
            userRepository.checkUser(checkUserRequestDto)
                .collect {
                    if (it is Response.Failure && it.e is HttpException) {
                        when (it.e.code()) {
                            400 -> onUsedPhoneNumber()
                            409 -> onMoveLoginScreen()
                        }
                    } else {
                        onMoveSignupScreen()
                    }
                }
        }
    }

    fun reLogin(
        context: Context,
        onMoveLoginDoneScreen: () -> Unit,
        onErrorPassword: () -> Unit
    ) {
        viewModelScope.launch {
            val reLoginRequestDto = ReLoginRequestDto(
                phoneNumber = phoneNumber.value,
                password = password.value
            )
            userRepository.reLogin(reLoginRequestDto)
                .collect {
                    if (it is Response.Failure && it.e is HttpException) {
                        when (it.e.code()) {
                            401 -> onErrorPassword()
                        }
                    } else if (it is Response.Success) {
                        saveUserInfo(context, it.data)
                        onMoveLoginDoneScreen()
                    }
                }
        }
    }

    fun signup(
        context: Context,
        onMoveLoginDoneScreen: () -> Unit
    ) {
        viewModelScope.launch {
            val signupRequestDto = SignupRequestDto(
                userName = name.value,
                phoneNumber = phoneNumber.value,
                password = password.value,
                birthday = birthday.value,
                sex = (Random.nextInt(0, 10) % 2) + 1
            )
            userRepository.signup(signupRequestDto)
                .collect {
                    if (it is Response.Success) {
                        saveUserInfo(context, it.data)
                        onMoveLoginDoneScreen()
                    }
                }
        }
    }

    fun login(
        context: Context,
        onMoveLoginDoneScreen: () -> Unit
    ) {
        viewModelScope.launch {
            UserStore(context).getValue(UserStore.KEY_REFRESH_TOKEN)
                .collect {
                    val loginRequestDto = LoginRequestDto(
                        refreshToken = it,
                        password = password.value
                    )
                    userRepository.login(loginRequestDto)
                        .collect { res ->
                            if (res is Response.Success) {
                                saveUserInfo(context, res.data)
                                onMoveLoginDoneScreen()
                            }
                        }
                }
        }
    }

    private suspend fun saveUserInfo(
        context: Context,
        loginResponseDto: LoginResponseDto
    ) {
        UserStore(context).setValue(UserStore.KEY_PASSWORD, password.value)
            .setValue(UserStore.KEY_REFRESH_TOKEN, loginResponseDto.refreshToken)
            .setValue(UserStore.KEY_USER_ID, loginResponseDto.userId)
            .setValue(UserStore.KEY_USER_NAME, loginResponseDto.userName)
            .setValue(UserStore.KEY_ACCESS_TOKEN, loginResponseDto.accessToken)
    }
}
