package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.request.ChangeProfileRequestDto
import com.finance.android.domain.dto.response.DailyProfileResponseDto
import com.finance.android.domain.dto.response.UserProfileResponseDto
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.DailyRepository
import com.finance.android.domain.repository.UserRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val userRepository: UserRepository,
    private val dailyRepository: DailyRepository
) : BaseViewModel(application, baseRepository) {
    val myInfo = mutableStateOf<Response<UserProfileResponseDto>>(Response.Loading)
    val profileList = mutableStateOf<Response<MutableList<DailyProfileResponseDto>>>(Response.Loading)

    fun launchMyPage() {
        viewModelScope.launch {
            getUserInfo()
            getProfileList()
        }
    }

    fun callChangeProfile(profileId : Int) {
        viewModelScope.launch {
            changeUserProfile(ChangeProfileRequestDto(profileId))
        }
    }

    fun getLoadState(): Response<Unit> {
        val arr = arrayOf(myInfo, profileList)

        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }

    private suspend fun changeUserProfile(changeProfileRequestDto: ChangeProfileRequestDto) {
        this@MyPageViewModel.run {
            dailyRepository.changeProfile(changeProfileRequestDto)
        }.collect {
            if(it is Response.Success) {
                getProfileList()
            }
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

    private suspend fun getProfileList() {
        this@MyPageViewModel.run {
            dailyRepository.getProfileList()
        }.collect {
            profileList.value = it
            if(it is Response.Success) {
                println(it.data.toString())
            }
        }
    }
}
