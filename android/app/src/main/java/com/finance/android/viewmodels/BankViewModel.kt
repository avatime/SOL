package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.response.BankAccountResponseDto
import com.finance.android.domain.dto.response.BankInfoResponseDto
import com.finance.android.domain.dto.response.RecentTradeResponseDto
import com.finance.android.domain.repository.BankRepository
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    savedStateHandle: SavedStateHandle = SavedStateHandle(),
    private val bankRepository: BankRepository
) : BaseViewModel(application, baseRepository) {
    val accountList =
        mutableStateOf<Response<MutableList<BankAccountResponseDto>>>(Response.Loading)

    fun myAccountLoad() {
        viewModelScope.launch {
            loadMyAccountList()
        }
    }

    fun getLoadState(): Response<Unit> {
        val arr = arrayOf(accountList)

        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }

    private suspend fun loadMyAccountList() {
        this@BankViewModel.run {
            bankRepository.getMyAccount()
        }
            .collect {
                accountList.value = it
            }
    }

    val accountBalance = mutableStateOf<Response<Int>>(Response.Loading)

    fun getLoadAccountBalance(): Response<Unit> {
        val arr = arrayOf(accountBalance)

        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }

    fun loadAccountBalance(
        acNo: String
    ) {
        viewModelScope.launch {
            this@BankViewModel.run {
                bankRepository.getAccountBalance(
                    acNo = acNo
                )
            }.collect {
                accountBalance.value = it
            }
        }
    }

}