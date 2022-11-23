package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.datastore.UserStore
import com.finance.android.domain.repository.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputPasswordViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository
) : BaseViewModel(application, baseRepository) {

    val useBio = mutableStateOf(false)
    private val savePassword = mutableStateOf("")
    val password = mutableStateOf("")
    val errorPassword = mutableStateOf(false)
    val showBioInfoDialog = mutableStateOf(false)

    init {
        viewModelScope.launch {
            UserStore(getApplication()).getValue(UserStore.KEY_USE_BIO)
                .collect {
                    useBio.value = it == "1"
                }
            UserStore(getApplication()).getValue(UserStore.KEY_PASSWORD)
                .collect {
                    savePassword.value = it
                }
        }
    }

    fun onClickUserBioButton() {
        viewModelScope.launch {
            useBio.value = !useBio.value
            if (useBio.value) {
                showBioInfoDialog.value = true
            }
            UserStore(getApplication()).setValue(
                UserStore.KEY_USE_BIO,
                if (useBio.value) "1" else "0"
            )
        }
    }

    fun onValueChange(it: String) {
        if (it.length <= 6 && it != password.value) {
            password.value = it
            errorPassword.value = false
        }
    }

    fun onClickNextButton(onSuccess: () -> Unit) {
        if (password.value == savePassword.value) {
            onSuccess()
        } else {
            errorPassword.value = true
            password.value = ""
        }
    }

    fun enableOnClickNextButton(): Boolean {
        return password.value.length == 6
    }
}
