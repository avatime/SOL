package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.response.FinanceResponseDto
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.StockRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val stockRepository: StockRepository
) : BaseViewModel(application, baseRepository) {
    val stockList = mutableStateListOf<FinanceResponseDto>()

    fun launch() {
        viewModelScope.launch {
            loadStockList()
        }
    }

    private suspend fun loadStockList() {
        this@StockViewModel.run {
            stockRepository.getFinanceList()
        }
            .collect {
                if (it is Response.Success) {
                    stockList.clear()
                    stockList.addAll(it.data)
                }
            }
    }
}