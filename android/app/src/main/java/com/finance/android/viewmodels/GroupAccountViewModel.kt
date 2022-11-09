package com.finance.android.viewmodels

import android.app.Application

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
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
    private val groupAccountRepository: GroupAccountRepository
) : BaseViewModel(application, baseRepository) {

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


}