package com.finance.android.viewmodels

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.datastore.WalkStore
import com.finance.android.domain.dto.response.AccountRegisteredResponseDto
import com.finance.android.domain.dto.response.FinanceResponseDto
import com.finance.android.domain.repository.BankRepository
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.StockRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val bankRepository: BankRepository,
    private val stockRepository: StockRepository
) : BaseViewModel(application, baseRepository), SensorEventListener {
    val mainData = mutableStateOf<Response<AccountRegisteredResponseDto>>(Response.Loading)
    val stockList = mutableStateOf<Array<FinanceResponseDto>>(arrayOf())
    val walkCount = mutableStateOf<Int?>(null)

    fun load() {
        viewModelScope.launch {
            loadList()
            loadWalkCount()
            loadStockList()
        }
    }

    fun getLoadState(): Response<Unit> {
        val arr = arrayOf(mainData)

        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }

    private suspend fun loadList() {
        this@HomeViewModel.run {
            bankRepository.getAllMainAccount()
        }
            .collect {
                mainData.value = it
            }
    }

    private suspend fun loadWalkCount() {
        WalkStore(getApplication()).getCount().collect {
            walkCount.value = it
        }
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        viewModelScope.launch {
            loadWalkCount()
        }
    }

    private suspend fun loadStockList() {
        this@HomeViewModel.run {
            stockRepository.getFinanceList()
        }
            .collect {
                if (it is Response.Success) {
                    stockList.value = it.data
                }
            }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}