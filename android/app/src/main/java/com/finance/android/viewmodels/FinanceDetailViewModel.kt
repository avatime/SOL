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
class FinanceDetailViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    savedStateHandle: SavedStateHandle,
    private val stockRepository: StockRepository
) : BaseViewModel(application, baseRepository) {
    val fnName = savedStateHandle.get<String>("fnName")!!
    val financeDetailList =
        mutableStateOf<Response<Array<FinanceDetailResponseDto>>>(Response.Loading)

    fun Load() {
        viewModelScope.launch {
            loadFinanceDetailList(fnName)
        }
    }

    fun getLoadState(): Response<Unit> {
        val arr = arrayOf(financeDetailList)

        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }

    private suspend fun loadFinanceDetailList(fnName: String) {
        this@FinanceDetailViewModel.run {
            stockRepository.getFinanceDetailList(fnName)
        }
            .collect {
                financeDetailList.value = it
            }
    }
}