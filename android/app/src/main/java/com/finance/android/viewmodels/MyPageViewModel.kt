package com.finance.android.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.response.DailyAttendanceResponseDto
import com.finance.android.domain.dto.response.UserProfileResponseDto
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.DailyRepository
import com.finance.android.domain.repository.SampleRepository
import com.finance.android.domain.repository.UserRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class MyPageViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val userRepository: UserRepository,
) : BaseViewModel(application, baseRepository) {
    val myInfo = mutableStateOf<Response<UserProfileResponseDto>>(Response.Loading)

    fun launchMyPage() {
        viewModelScope.launch {
            getUserInfo()
        }
    }

    fun getLoadState(): Response<Unit> {
        val arr = arrayOf(myInfo)

        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }

    private suspend fun getUserInfo() {
        this@MyPageViewModel.run {
            userRepository.getUserProfile()
        }.collect {
            myInfo.value = it
//            if(it is Response.Success) {
//            }
        }
    }
}
