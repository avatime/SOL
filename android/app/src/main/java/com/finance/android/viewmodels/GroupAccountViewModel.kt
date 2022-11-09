package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.request.CreateGroupAccountRequestDto
import com.finance.android.domain.dto.request.MemberRequestDto
import com.finance.android.domain.dto.response.PublicAccountResponseDto
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.GroupAccountRepository
import com.finance.android.domain.repository.StockRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupAccountViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val groupAccountRepository: GroupAccountRepository
) : BaseViewModel(application, baseRepository){

    val name = mutableStateOf("")


//    // 모임 통장 생성
//    fun CreateGroupAccount(
//
//    ){
//        viewModelScope.launch {
//            this@GroupAccountViewModel.run{
//                groupAccountRepository.postMakeGroupAccount(
//                    CreateGroupAccountRequestDto(
//                        name = name.value,
//                        memberList = List<MemberRequestDto>
//                    )
//                )
//            }
//        }
//    }


    //모임 통장 조회
    private val _groupAccountData = mutableStateOf<Response<MutableList<PublicAccountResponseDto>>>(Response.Loading)
    val groupAccountData = _groupAccountData
    fun getGroupAccountData() {
        viewModelScope.launch {
            this@GroupAccountViewModel.run {
                groupAccountRepository.getGroupAccount()
            }.collect{
                _groupAccountData.value = it
            }
        }
    }

    //모임통장생성시 친구리스트 만들기
    var isSelect = mutableStateOf(false)

    fun selectFriend () {
        isSelect.value = !isSelect.value
    }



}