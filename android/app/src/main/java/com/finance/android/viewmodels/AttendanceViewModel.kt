package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.response.DailyAttendanceResponseDto
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.DailyRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val dailyRepository: DailyRepository,
) : BaseViewModel(application, baseRepository) {
    val isAttend = mutableStateOf(false)
    val attendanceList = mutableStateOf<Response<MutableList<DailyAttendanceResponseDto>>>(Response.Loading)

    fun launchAttendance() {
        viewModelScope.launch {
            loadAttendanceList(LocalDateTime.now().year, LocalDateTime.now().monthValue)
        }
    }

    fun onClickIsAttend() {
        isAttend.value = !isAttend.value
        viewModelScope.launch {
            checkAttendance { loadAttendanceList(LocalDateTime.now().year, LocalDateTime.now().monthValue) }
        }
    }

    fun getLoadState(): Response<Unit> {
        val arr = arrayOf(attendanceList)

        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }

    private suspend fun checkAttendance(onSuccess: suspend () -> Unit) {
        this@AttendanceViewModel.run {
            dailyRepository.attendance()
        }.collect { res ->
            if (res is Response.Success) {
                onSuccess()
            }
        }
    }

    private suspend fun loadAttendanceList(year : Int, month : Int) {
        this@AttendanceViewModel.run {
            dailyRepository.getAttendanceList(year, month)
        }
            .collect {
                attendanceList.value = it
                if(it is Response.Success) {
                    isAttend.value = it.data[LocalDate.now().dayOfMonth - 1].attendance
                }
            }
    }
}
