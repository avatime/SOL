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

    fun RegisteredCardLoad() {
        viewModelScope.launch {
            loadRegisteredCardList()
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

    private suspend fun loadRegisteredCardList() {
        this@CardViewModel.run {
            cardRepository.getMyCardList()
        }
            .collect {
                cardList.value = it
            }
    }

}