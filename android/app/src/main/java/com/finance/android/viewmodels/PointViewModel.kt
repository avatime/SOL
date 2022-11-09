package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.response.PointHistoryResponseDto
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.PointRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PointViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val pointRepository: PointRepository,
) : BaseViewModel(application, baseRepository) {
    val pointHistoryList = mutableStateOf<Response<MutableList<PointHistoryResponseDto>>>(Response.Loading)

    fun launchPointHistory() {
        viewModelScope.launch {
            loadPointHistoryAllList()
        }
    }

    fun getLoadState(): Response<Unit> {
        val arr = arrayOf(pointHistoryList)

        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }

    private suspend fun loadPointHistoryAllList() {
        this@PointViewModel.run {
            pointRepository.getPointAllList()
        }
            .collect {
                pointHistoryList.value = it
//                if(it is Response.Success) {
//                }
            }
    }
}