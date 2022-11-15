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
        mutableStateOf<Array<CardResponseDto>?>(null)

    fun myCardLoad() {
        viewModelScope.launch {
            loadMyCardList()
        }
    }

    private suspend fun loadMyCardList() {
        this@CardViewModel.run {
            cardRepository.getMyCardList()
        }
            .collect {
                if (it is Response.Success) {
                    cardList.value = it.data
                }
            }
    }

    // 카드 혜택 조회

    val cardBenefit = mutableStateOf<Response<MutableList<CardBenefitInfoResponseDto>>>(Response.Loading)
    val cardBenefitDetail = mutableStateOf<Response<MutableList<CardBenefitDetailResponseDto>>>(Response.Loading)

    fun getLoadCardBenefit(): Response<Unit> {
        val arr = arrayOf(cardBenefit, cardBenefitDetail)

        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }

    fun loadCardBenefit(
        cardProductCode: Int
    ) {
        viewModelScope.launch {
            this@CardViewModel.run {
                cardRepository.getCardBenefit(
                    cardProductCode = cardProductCode,
                )
            }.collect {
                cardBenefit.value = it
            }
            this@CardViewModel.run {
                cardRepository.getCardBenefitDetail(
                    cardProductCode = cardProductCode,
                )
            }.collect {
                cardBenefitDetail.value = it
            }
        }
    }

    // 카드 거래 기록 조회

    val cardHistory = mutableStateOf<Response<MutableList<CardBillDetailResponseDto>>>(Response.Loading)

    fun getLoadCardHistory(): Response<Unit> {
        val arr = arrayOf(cardHistory)

        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }

    fun loadCardHistory(
        cdNo: String
    ) {
        viewModelScope.launch {
            this@CardViewModel.run {
                cardRepository.getCardHistory(
                    cdNo = cdNo,
                )
            }.collect {
                cardHistory.value = it
            }
        }
    }
}