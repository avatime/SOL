package com.finance.android.viewmodels

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.datastore.WalkStore
import com.finance.android.domain.dto.response.AccountRegisteredResponseDto
import com.finance.android.domain.repository.BankRepository
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val bankRepository: BankRepository,
) : BaseViewModel(application, baseRepository), SensorEventListener {
    val mainData = mutableStateOf<Response<AccountRegisteredResponseDto>>(Response.Loading)
    val walkCount = mutableStateOf<Int?>(null)

    fun load() {
        viewModelScope.launch {
            loadList()
            loadWalkCount()
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

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}