package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.response.FinanceDetailResponseDto
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.StockRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockDetailViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    savedStateHandle: SavedStateHandle,
    private val stockRepository: StockRepository
) : BaseViewModel(application, baseRepository) {
    val fnName = savedStateHandle.get<String>("fnName")!!

    val stockDetailList = mutableStateOf<Array<FinanceDetailResponseDto>>(emptyArray())
    val periodType = mutableStateOf(PeriodType.WEEK)

    fun launch() {
        viewModelScope.launch {
            loadFinanceDetailList()
        }
    }

    fun onClickPeriod(periodType: PeriodType) {
        this.periodType.value = periodType
    }

    private suspend fun loadFinanceDetailList() {
        this@StockDetailViewModel.run {
            stockRepository.getFinanceDetailList(fnName)
        }
            .collect {
                if (it is Response.Success) {
                    stockDetailList.value = it.data
                }
            }
    }
}

enum class PeriodType(
    val value: String,
    val period: Int,
    val stroke: Int
) {
    WEEK("1주", 7, 5),
    MONTH("1달", 30, 5),
    THREE_MONTH("3달", 90, 4),
    HALF_YEAR("6달", 180, 4),
    YEAR("1년", 360, 3),
    TWO_YEAR("2년", 100000, 2);
}
