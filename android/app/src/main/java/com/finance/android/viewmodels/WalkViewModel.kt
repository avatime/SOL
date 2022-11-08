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
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class WalkViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val dailyRepository: DailyRepository,
) : BaseViewModel(application, baseRepository), SensorEventListener {
    val walkingList = mutableStateOf<Response<MutableList<DailyWalkingResponseDto>>>(Response.Loading)
    val walkCount = mutableStateOf<Int?>(null)

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

    private suspend fun loadWalkingList(year : Int, month : Int) {
        this@WalkViewModel.run {
            dailyRepository.getWalkingList(year, month)
        }
            .collect {
                walkingList.value = it
//                if(it is Response.Success) {
//
//                }
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
