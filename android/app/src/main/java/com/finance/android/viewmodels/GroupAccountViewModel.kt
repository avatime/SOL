package com.finance.android.viewmodels

import android.app.Application

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.request.CreateGroupAccountRequestDto
import com.finance.android.domain.dto.request.GroupIdRequestDto
import com.finance.android.domain.dto.response.FriendResponseDto
import com.finance.android.domain.dto.response.PublicAccountResponseDto
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.GroupAccountRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupAccountViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    savedStateHandle: SavedStateHandle,
    private val groupAccountRepository: GroupAccountRepository
) : BaseViewModel(application, baseRepository) {

    val name = mutableStateOf("")
    val paId = mutableStateOf(0)

    //모임 통장 조회
    private val _groupAccountData =
        mutableStateOf<Response<MutableList<PublicAccountResponseDto>>>(Response.Loading)
    val groupAccountData = _groupAccountData
    fun getGroupAccountData() {
        viewModelScope.launch {
            this@GroupAccountViewModel.run {
                groupAccountRepository.getGroupAccount()
            }.collect {
                _groupAccountData.value = it
            }
        }
    }

    //모임통장생성시 친구리스트 만들기
    var selectFriendsList: Array<MutableState<Boolean>>? = null

    fun initSelectedFriendsList(size: Int) {
        if (selectFriendsList != null) {
            return
        }
        selectFriendsList = Array(size) { mutableStateOf(false) }
    }

    fun onClickFriend(index: Int) {
        if (selectFriendsList == null) {
            return
        }
        selectFriendsList!![index].value = !selectFriendsList!![index].value
        println(selectFriendsList!![index].value)
        Log.i("group", "${selectFriendsList!![index].value}")
        Log.i("group", "$index")
    }

    // 모임 통장 생성
    fun makeGroupAccount(
        createGroupAccountRequestDto: CreateGroupAccountRequestDto,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            this@GroupAccountViewModel.run {
                groupAccountRepository.postMakeGroupAccount(
                    createGroupAccountRequestDto
                )
            }.collect {
                if (it is Response.Success) {
                    Log.i("remitAccount", "전번도 갓찬영")
                    onSuccess()
                } else if (it is Response.Failure) {
                    Log.i("remitAccount", "전번도 김챤챤영 ㅡㅡ")
                }
            }
        }

    }
    fun onClickDeleteFriend(index: Int){
        selectFriendsList!![index].value = false
    }




    // 모임 친구 조회
    private val _groupAccountMemberData =
        mutableStateOf<Response<MutableList<FriendResponseDto>>>(Response.Loading)
    val groupAccountMemberData = _groupAccountMemberData
    fun getGroupAccountMember(paId: Int) {

        viewModelScope.launch {
            this@GroupAccountViewModel.run {
                groupAccountRepository.postGroupMember(GroupIdRequestDto(paId))
            }.collect {
                _groupAccountMemberData.value = it
            }
        }
    }
}


