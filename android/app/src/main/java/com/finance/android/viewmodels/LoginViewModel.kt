package com.finance.android.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.repository.UserRepository
import com.finance.android.ui.fragments.LoginStep
import com.finance.android.ui.screens.login.InputUserInfoStep
import com.finance.android.utils.Response
import com.finance.android.utils.validateBirthday
import com.finance.android.utils.validatePhoneNum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

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
        loginStep: LoginStep,
        step: InputUserInfoStep? = null
    ): Boolean {
        return when (loginStep) {
            LoginStep.InputUserInfo -> when (step!!) {
                InputUserInfoStep.NAME -> name.value.isNotEmpty()
                InputUserInfoStep.BIRTHDAY -> validateBirthday(birthday.value)
                InputUserInfoStep.PHONE_NUM -> validatePhoneNum(phoneNumber.value)
            }
            LoginStep.TestPhone     -> code.value.isNotEmpty() && rightCode.value is Response.Success<String> && (rightCode.value as Response.Success<String>).data.equals(code.value)
            LoginStep.InputPassword -> {
                if (passwordRepeat.value == null) {
                    password.value.length == 6
                } else {
                    password.value == passwordRepeat.value
                }
            }
            LoginStep.Done          -> true
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
}
