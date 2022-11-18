package com.finance.android.viewmodels

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.datastore.WalkStore
import com.finance.android.domain.dto.request.ReceivePointDto
import com.finance.android.domain.dto.response.AccountRegisteredResponseDto
import com.finance.android.domain.dto.response.FinanceResponseDto
import com.finance.android.domain.dto.response.UserProfileResponseDto
import com.finance.android.domain.repository.BankRepository
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.StockRepository
import com.finance.android.domain.repository.UserRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val bankRepository: BankRepository,
    private val stockRepository: StockRepository,
    private val userRepository: UserRepository

) : BaseViewModel(application, baseRepository), SensorEventListener {
    val mainData = mutableStateOf<AccountRegisteredResponseDto?>(null)
    val stockList = mutableStateOf<Array<FinanceResponseDto>>(arrayOf())
    val walkCount = mutableStateOf<Int?>(null)
    val point = mutableStateOf<Int?>(null)

    fun load() {
        viewModelScope.launch {
            loadData()
            loadWalkCount()
        }
    }

    private suspend fun loadData() {
        this@HomeViewModel.run {
            arrayOf(
                bankRepository.getAllMainAccount(),
                stockRepository.getHomeFinanceList(),
                userRepository.getUserProfile()
            )
        }.collect {
            if (it is Response.Success) {
                mainData.value = it.data[0] as AccountRegisteredResponseDto
                stockList.value =
                    (it.data[1] as Array<*>).map { v -> v as FinanceResponseDto }.toTypedArray()
                point.value = (it.data[2] as UserProfileResponseDto).point
            }
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

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    fun onEasterEgg() {
        viewModelScope.launch {
            userRepository.receivePoint(
                ReceivePointDto(
                    name = "이스터에그 발견!",
                    point = 100
                )
            )
        }
    }
}
