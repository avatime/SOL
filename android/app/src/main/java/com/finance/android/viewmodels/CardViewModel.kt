package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.response.*
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.CardRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val cardRepository: CardRepository
) : BaseViewModel(application, baseRepository) {
    val cardList =
        mutableStateOf<Response<MutableList<CardResponseDto>>>(Response.Loading)

    fun myCardLoad() {
        viewModelScope.launch {
            loadMyCardList()
        }
    }

    fun getLoadState(): Response<Unit> {
        val arr = arrayOf(cardList)

        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }

    private suspend fun loadMyCardList() {
        this@CardViewModel.run {
            cardRepository.getMyCardList()
        }
            .collect {
                cardList.value = it
            }
    }

    // 카드 청구서 조회

    val cardBill = mutableStateOf<Response<CardBillResponseDto>>(Response.Loading)

    fun getLoadCardBill(): Response<Unit> {
        val arr = arrayOf(cardBill)

        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }

    fun loadCardBill(
        cdNo: String,
        year: Int,
        month: Int
    ) {
        viewModelScope.launch {
            this@CardViewModel.run {
                cardRepository.getCardBill(
                    cdNo = cdNo,
                    year = year,
                    month = month
                )
            }.collect {
                cardBill.value = it
            }
        }
    }


}