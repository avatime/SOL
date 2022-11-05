package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.response.FinanceDetailResponseDto
import com.finance.android.domain.dto.response.FinanceResponseDto
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.StockRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FinanceViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val stockRepository: StockRepository
) : BaseViewModel(application, baseRepository){
    val financeList = mutableStateOf<Response<Array<FinanceResponseDto>>>(Response.Loading)
    val financeDetailList = mutableStateOf<Response<Array<FinanceDetailResponseDto>>>(Response.Loading)
    fun Load() {
       viewModelScope.launch {
           loadFinanceList()
           loadFinanceDetailList("삼성전자")
       }
    }

    fun getLoadState(): Response<Unit> {
        val arr = arrayOf(financeList, financeDetailList)

        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }

    private suspend fun loadFinanceList() {
        this@FinanceViewModel.run {
            stockRepository.getFinanceList()
        }
            .collect {
                financeList.value = it
            }
    }

    private suspend fun loadFinanceDetailList(fnName: String) {
        this@FinanceViewModel.run {
            stockRepository.getFinanceDetailList(fnName)
        }
            .collect {
                financeDetailList.value = it
            }
    }
}