package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.request.MainAccountDto
import com.finance.android.domain.dto.response.BankAccountResponseDto
import com.finance.android.domain.dto.response.BankTradeResponseDto
import com.finance.android.domain.repository.BankRepository
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.UserRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    savedStateHandle: SavedStateHandle,
    private val bankRepository: BankRepository,
    private val userRepository: UserRepository
) : BaseViewModel(application, baseRepository) {

    val acMain = mutableStateOf(savedStateHandle.get<Int>("acMain"))
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

    // 계좌 잔액, 거래 내역 불러오기

    val accountBalance = mutableStateOf<Response<Int>>(Response.Loading)
    val accountHistory = mutableStateOf<Response<MutableList<BankTradeResponseDto>>>(Response.Loading)

    fun getLoadAccountBalanceandHistory(): Response<Unit> {
        val arr = arrayOf(accountBalance, accountHistory)

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

    fun loadAccountHistory(
        acNo: String
    ) {
        viewModelScope.launch {
            this@BankViewModel.run {
                bankRepository.getAccountDetail(
                    acNo = acNo
                )
            }.collect {
                accountHistory.value = it
            }
        }
    }

    fun setRepAccount(acNo: String) {
        println(acNo)
        viewModelScope.launch {
            this@BankViewModel.run {
                val mainAccountDto = MainAccountDto(acNo)
                userRepository.changeRepAccount(
                    mainAccountDto
                )
            }.collect {
                acMain.value = 1
            }
        }
    }

}