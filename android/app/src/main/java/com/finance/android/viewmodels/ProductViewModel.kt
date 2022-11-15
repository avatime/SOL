package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.response.CardRecommendResponseDto
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.CardRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel  @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val cardRepository: CardRepository
) : BaseViewModel(application, baseRepository) {

    val cardRecommendList = mutableStateOf<CardRecommendResponseDto?>(null)

    fun loadCardRecommend() {
        viewModelScope.launch {
            this@ProductViewModel.run {
                cardRepository.getCardRecommend()
            }.collect {
                if (it is Response.Success) {
                    cardRecommendList.value = it.data
                }
            }
        }
    }
}