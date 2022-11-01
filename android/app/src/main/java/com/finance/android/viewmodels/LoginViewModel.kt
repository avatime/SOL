package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.datastore.UserStore
import com.finance.android.domain.RetrofitClient
import com.finance.android.domain.dto.request.CheckUserRequestDto
import com.finance.android.domain.dto.request.LoginRequestDto
import com.finance.android.domain.dto.request.ReLoginRequestDto
import com.finance.android.domain.dto.request.SignupRequestDto
import com.finance.android.domain.dto.response.LoginResponseDto
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.UserRepository
import com.finance.android.ui.fragments.SignupStep
import com.finance.android.ui.screens.login.InputUserInfoStep
import com.finance.android.utils.Response
import com.finance.android.utils.validateBirthday
import com.finance.android.utils.validatePhoneNum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val userRepository: UserRepository
) : BaseViewModel(application, baseRepository) {

    val name = mutableStateOf("")
    val birthday = mutableStateOf("")
    val phoneNumber = mutableStateOf("")

    val code = mutableStateOf("")
    private val rightCode = mutableStateOf<Response<String>>(Response.Loading)

    val password = mutableStateOf("")
    val passwordRepeat = mutableStateOf<String?>(null)

    val useBio = mutableStateOf(false)

    init {
        viewModelScope.launch {
            UserStore(getApplication()).getValue(UserStore.KEY_USE_BIO)
                .collect {
                    useBio.value = it == "1"
                }
        }
    }

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
            SignupStep.TestPhone -> code.value.isNotEmpty() && rightCode.value is Response.Success<String> && (rightCode.value as Response.Success<String>).data.equals(code.value)
            SignupStep.InputPassword -> {
                if (passwordRepeat.value == null) {
                    password.value.length == 6
                } else {
                    password.value == passwordRepeat.value
                }
            }
            SignupStep.Done -> true
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
            val checkUserRequestDto = CheckUserRequestDto(
                userName = name.value,
                phoneNumber = phoneNumber.value,
                birthday = formatBirthday()
            )
            this@LoginViewModel.run { userRepository.checkUser(checkUserRequestDto) }
                .collect {
                    if (it is Response.Failure && it.e is HttpException) {
                        when (it.e.code()) {
                            400 -> onUsedPhoneNumber()
                            409 -> onMoveLoginScreen()
                        }
                    } else if (it is Response.Success) {
                        onMoveSignupScreen()
                    }
                }
        }
    }

    fun reLogin(
        onMoveLoginDoneScreen: () -> Unit,
        onErrorPassword: () -> Unit
    ) {
        viewModelScope.launch {
            val reLoginRequestDto = ReLoginRequestDto(
                phoneNumber = phoneNumber.value,
                password = password.value
            )
            this@LoginViewModel.run { userRepository.reLogin(reLoginRequestDto) }
                .collect {
                    if (it is Response.Failure && it.e is HttpException) {
                        when (it.e.code()) {
                            401 -> onErrorPassword()
                        }
                    } else if (it is Response.Success) {
                        saveUserInfo(it.data)
                        onMoveLoginDoneScreen()
                    }
                }
        }
    }

    fun signup(
        onMoveLoginDoneScreen: () -> Unit
    ) {
        viewModelScope.launch {
            val signupRequestDto = SignupRequestDto(
                userName = name.value,
                phoneNumber = phoneNumber.value,
                password = password.value,
                birthday = formatBirthday(),
                sex = (Random.nextInt(0, 10) % 2) + 1
            )
            this@LoginViewModel.run { userRepository.signup(signupRequestDto) }
                .collect {
                    if (it is Response.Success) {
                        saveUserInfo(it.data)
                        onMoveLoginDoneScreen()
                    }
                }
        }
    }

    fun login(
        onSuccess: () -> Unit,
        onErrorPassword: () -> Unit
    ) {
        viewModelScope.launch {
            UserStore(getApplication()).getValue(UserStore.KEY_REFRESH_TOKEN)
                .collect { token ->
                    val loginRequestDto = LoginRequestDto(
                        refreshToken = token,
                        password = password.value
                    )
                    this@LoginViewModel.run { userRepository.login(loginRequestDto) }
                        .collect {
                            if (it is Response.Failure && it.e is HttpException) {
                                when (it.e.code()) {
                                    401 -> onErrorPassword()
                                }
                            } else if (it is Response.Success) {
                                saveUserInfo(it.data)
                                onSuccess()
                            }
                        }
                }
        }
    }

    fun autoLogin(
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            UserStore(getApplication()).getValue(UserStore.KEY_REFRESH_TOKEN)
                .combine(UserStore(getApplication()).getValue(UserStore.KEY_PASSWORD)) { token, pass ->
                    arrayOf(token, pass)
                }
                .collect {
                    val loginRequestDto = LoginRequestDto(
                        refreshToken = it[0],
                        password = it[1]
                    )
                    this@LoginViewModel.run { userRepository.login(loginRequestDto) }
                        .collect { res ->
                            if (res is Response.Success) {
                                saveUserInfo(res.data)
                                onSuccess()
                            }
                        }
                }
        }
    }

    private suspend fun saveUserInfo(
        loginResponseDto: LoginResponseDto
    ) {
        with(loginResponseDto) {
            RetrofitClient.login(accessToken, refreshToken)
            UserStore(getApplication()).setValue(UserStore.KEY_PASSWORD, password.value)
                .setValue(UserStore.KEY_REFRESH_TOKEN, refreshToken)
                .setValue(UserStore.KEY_USER_ID, userId)
                .setValue(UserStore.KEY_USER_NAME, userName)
                .setValue(UserStore.KEY_ACCESS_TOKEN, accessToken)
        }
        UserStore(getApplication()).setValue(
            UserStore.KEY_USE_BIO,
            if (useBio.value) "1" else "0"
        )
    }

    private fun formatBirthday(): String {
        return birthday.value.let {
            val result = "${it.substring(0..1)}-${it.substring(2..3)}-${it.substring(4..5)}"
            return@let if (it.substring(0..1)
                .toInt() < 10
            ) {
                "20$result"
            } else {
                "19$result"
            }
        }
    }
}
