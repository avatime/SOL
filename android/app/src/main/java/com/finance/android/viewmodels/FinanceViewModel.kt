package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.response.BankAccountResponseDto
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
    val myFinanceList = mutableStateOf<Array<BankAccountResponseDto>?>(null)

    fun myFinanceLoad() {
        viewModelScope.launch {
            loadMyFinanceList()
        }
    }

    private suspend fun loadMyFinanceList() {
        this@FinanceViewModel.run {
            stockRepository.getMyFinanceList()
        }
            .collect {
                if (it is Response.Success) {
                    myFinanceList.value = it.data
                }
            }
    }

}