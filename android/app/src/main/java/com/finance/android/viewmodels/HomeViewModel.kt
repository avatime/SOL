package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.response.BankAccountResponseDto
import com.finance.android.domain.dto.response.BankInfoResponseDto
import com.finance.android.domain.dto.response.CardInfoResponseDto
import com.finance.android.domain.dto.response.RecentTradeResponseDto
import com.finance.android.domain.repository.BankRepository
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.CardRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val bankRepository: BankRepository,
    private val cardRepository: CardRepository
) : BaseViewModel(application, baseRepository) {
    val accountList =
        mutableStateOf<Response<MutableList<BankAccountResponseDto>>>(Response.Loading)

    val cardList =
        mutableStateOf<Response<MutableList<CardInfoResponseDto>>>(Response.Loading)

    fun Load() {
        viewModelScope.launch {
            loadAccountList()
            loadCardList()
        }
    }

    fun getLoadState(): Response<Unit> {
        val arr = arrayOf(accountList, cardList)

        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }

    private suspend fun loadAccountList() {
        this@HomeViewModel.run {
            bankRepository.getAccountList()
        }
            .collect {
                accountList.value = it
            }
    }

    private suspend fun loadCardList() {
        this@HomeViewModel.run {
            cardRepository.getCardList()
        }
            .collect {
                cardList.value = it
            }
    }

}