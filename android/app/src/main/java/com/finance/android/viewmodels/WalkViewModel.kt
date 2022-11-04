package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.response.DailyAttendanceResponseDto
import com.finance.android.domain.dto.response.DailyWalkingResponseDto
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.DailyRepository
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
    private val dailyRepository: DailyRepository,
) : BaseViewModel(application, baseRepository) {
    val walkingList = mutableStateOf<Response<MutableList<DailyWalkingResponseDto>>>(Response.Loading)

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
}
