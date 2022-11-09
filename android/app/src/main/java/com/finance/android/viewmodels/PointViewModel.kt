package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.response.PointHistoryResponseDto
import com.finance.android.domain.dto.response.UserProfileResponseDto
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.PointRepository
import com.finance.android.domain.repository.UserRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PointViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val pointRepository: PointRepository,
    private val userRepository: UserRepository
) : BaseViewModel(application, baseRepository) {
    val myInfo = mutableStateOf<Response<UserProfileResponseDto>>(Response.Loading)
    val pointHistoryList = mutableStateOf<Response<MutableList<PointHistoryResponseDto>>>(Response.Loading)

    fun launchPointHistory() {
        viewModelScope.launch {
            loadPointHistoryAllList()
            getUserInfo()
        }
    }

    fun getLoadState(): Response<Unit> {
        val arr = arrayOf(pointHistoryList, myInfo)

        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }

    private suspend fun getUserInfo() {
        this@PointViewModel.run {
            userRepository.getUserProfile()
        }.collect {
            myInfo.value = it
//            if(it is Response.Success) {
//            }
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