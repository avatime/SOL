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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val userRepository: UserRepository,
    private val dailyRepository: DailyRepository
) : BaseViewModel(application, baseRepository) {
    val myInfo = mutableStateOf<UserProfileResponseDto?>(null)
    val profileList = mutableStateOf<Array<DailyProfileResponseDto>>(arrayOf())

    fun launchMyPage() {
        viewModelScope.launch {
            this@MyPageViewModel.run {
                arrayOf(
                    userRepository.getUserProfile(),
                    dailyRepository.getProfileList()
                )
            }.collect {
                if (it is Response.Success) {
                    myInfo.value = it.data[0] as UserProfileResponseDto
                    profileList.value =
                        (it.data[1] as Array<*>).map { v -> v as DailyProfileResponseDto }
                            .toTypedArray()
                }
            }
        }
    }

    fun callChangeProfile(profileId: Int) {
        println(profileId)
        viewModelScope.launch {
            changeUserProfile(ChangeProfileRequestDto(profileNo = profileId))
        }
    }

    private suspend fun changeUserProfile(changeProfileRequestDto: ChangeProfileRequestDto) {
        this@MyPageViewModel.run {
            dailyRepository.changeProfile(changeProfileRequestDto)
        }.collect {
            if (it is Response.Success) {
                getUserInfo()
            }
        }
    }

    private suspend fun getUserInfo() {
        this@MyPageViewModel.run {
            userRepository.getUserProfile()
        }.collect {
            if (it is Response.Success) {
                myInfo.value = it.data
            }
        }
    }
}
