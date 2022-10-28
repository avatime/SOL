package com.finance.android.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.repository.UserRepository
import com.finance.android.ui.screens.login.InputUserInfoStep
import com.finance.android.utils.Response
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

    fun isPossibleGoNext(
        step: InputUserInfoStep
    ): Boolean {
        return when (step) {
            InputUserInfoStep.NAME -> name.value.isNotEmpty()
            InputUserInfoStep.BIRTHDAY -> birthday.value.length == 6
            InputUserInfoStep.PHONE_NUM -> phoneNumber.value.isNotEmpty()
        }
    }

    fun isRightCode() = code.value.isNotEmpty() && rightCode.value is Response.Success<String> && (rightCode.value as Response.Success<String>).data.equals(code.value)

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
