package com.finance.android.viewmodels

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.datastore.WalkStore
import com.finance.android.domain.dto.response.DailyWalkingResponseDto
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.DailyRepository
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class WalkViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val dailyRepository: DailyRepository
) : BaseViewModel(application, baseRepository), SensorEventListener {
    val walkingList = mutableStateOf<Response<MutableList<DailyWalkingResponseDto>>>(Response.Loading)
    val walkCount = mutableStateOf<Int?>(null)
    val enableReceiveWalkPoint = mutableStateOf(false)

    fun launchAttendance() {
        viewModelScope.launch {
            loadWalkingList(LocalDateTime.now().year, LocalDateTime.now().monthValue)
        }
    }

    fun getLoadState(): Response<Unit> {
        val arr = arrayOf(walkingList)

        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }

    private suspend fun loadWalkingList(year: Int, month: Int) {
        this@WalkViewModel.run {
            dailyRepository.getWalkingList(year, month)
        }
            .collect {
                walkingList.value = it
                if(it is Response.Success) {
                    enableReceiveWalkPoint.value = !it.data[LocalDate.now().dayOfMonth - 1].success && Const.GOAL_WALK_COUNT <= (walkCount.value ?: 0)
                }
            }
    }

    private suspend fun loadWalkCount() {
        WalkStore(getApplication()).getCount().collect {
            walkCount.value = it
            if (walkingList.value is Response.Success) {
                enableReceiveWalkPoint.value = !(walkingList.value as Response.Success).data[LocalDate.now().dayOfMonth - 1].success && Const.GOAL_WALK_COUNT <= it
            }
        }
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        viewModelScope.launch {
            loadWalkCount()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    fun receiveWalkPoint() {
        viewModelScope.launch {
            this@WalkViewModel.run {
                dailyRepository.receiveWalkPoint()
            }.collect {
                if (it is Response.Success) {
                    loadWalkingList(LocalDateTime.now().year, LocalDateTime.now().monthValue)
                }
            }
        }
    }
}
