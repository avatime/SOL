package com.finance.android.viewmodels

import android.app.Application

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.request.*
import com.finance.android.domain.dto.response.DuesResponseDto
import com.finance.android.domain.dto.response.FriendResponseDto
import com.finance.android.domain.dto.response.PublicAccountResponseDto
import com.finance.android.domain.dto.response.PublicTradeResponseDto
import com.finance.android.domain.repository.BankRepository
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.GroupAccountRepository
import com.finance.android.ui.components.AnimatedLoading
import com.finance.android.ui.components.showHistoryList
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GroupAccountViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    savedStateHandle: SavedStateHandle,
    private val groupAccountRepository: GroupAccountRepository,
    private val bankRepository: BankRepository
) : BaseViewModel(application, baseRepository) {

    val name = mutableStateOf("")
    val paId = mutableStateOf(0)
    val duesVal = mutableStateOf(0)
    val duesId = mutableStateOf(0)

    val duesName = mutableStateOf("")
    val duesBalance = mutableStateOf("")

    val isBackToMain = mutableStateOf(false)


    val mDate = mutableStateOf("")

    val goToMain = mutableStateOf(false)


    //1-> 회비입금 2-> 돈 입금 3-> 돈출금
    val screenType = mutableStateOf(0)

    val OKtext = mutableStateOf("")

    lateinit var list : MutableList<FriendResponseDto>


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
                    Log.i("group", "모임통장 생성")
                    onSuccess()
                } else if (it is Response.Failure) {
                    Log.i("group", "모임통장 생성 실패")
                }
            }
        }

    }

    fun onClickDeleteFriend(index: Int) {
        selectFriendsList!![index].value = false
    }


    // 회비 생성
    fun makeGroupDues(
        createDuesRequestDto: CreateDuesRequestDto,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            this@GroupAccountViewModel.run {
                groupAccountRepository.postRegistDues(
                    createDuesRequestDto
                )
            }.collect {
                if (it is Response.Success) {
                    Log.i("group", "회비도 갓찬영")
                    onSuccess()
                } else if (it is Response.Failure) {
                    Log.i("group", "회비도 김챤챤영 ㅡㅡ")
                }
            }
        }
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
                if(it is Response.Success){
                    _groupAccountMemberData.value = it
                }
            }
        }
    }

    // 회비 정보
    private val _groupAccountInfo =
        mutableStateOf<Response<PublicAccountResponseDto>>(Response.Loading)
    val groupAccountInfo = _groupAccountInfo
    fun postGroupAccountInfo(paId: Int) {
        viewModelScope.launch {
            this@GroupAccountViewModel.run {
                groupAccountRepository.postGroupAccountInfo(GroupIdRequestDto(paId))
            }.collect {
                _groupAccountInfo.value = it
            }
        }
    }

    //계좌잔액조회
    val representAccountBalance = mutableStateOf("")
    fun getRepresentAccountBalance() {
        viewModelScope.launch {
            this@GroupAccountViewModel.run {
                bankRepository.getRepresentAccountBalance()
            }.collect {
                if(it is Response.Success)   {
                    representAccountBalance.value = it.data
                    Log.i("group", "계좌 잔액 ㅎㅎㅎㅎ: ${representAccountBalance.value}")
                }
            }

            }
        }

        //전체 회비 조회
        private val _duesHistoryData =
            mutableStateOf<Response<MutableList<DuesResponseDto>>>(Response.Loading)
        val duesHistoryData = _duesHistoryData
        fun postDuesHistory(paId: Int) {
            viewModelScope.launch {
                this@GroupAccountViewModel.run {
                    groupAccountRepository.postDuesHistory(GroupIdRequestDto(paId))
                }.collect {
                    _duesHistoryData.value = it
                }
            }
        }


        // 입출금 내역 조회
        private val _duesTradeHistoryData =
            mutableStateOf<Response<MutableList<PublicTradeResponseDto>>>(Response.Loading)
        val duesTradeHistoryData = _duesTradeHistoryData
        fun getDuesTradeHistory(paId: Int) {
            viewModelScope.launch {
                this@GroupAccountViewModel.run {
                    groupAccountRepository.postTradeHistory(GroupIdRequestDto(paId))
                }.collect {
                    _duesTradeHistoryData.value = it
                }
            }
        }


        //회비입금
        fun postPayDues(remitDuesRequestDto: RemitDuesRequestDto, onSuccess: () -> Unit) {
            viewModelScope.launch {
                this@GroupAccountViewModel.run {
                    groupAccountRepository.postPayDues(remitDuesRequestDto)
                }.collect {
                    if (it is Response.Success) {
                        Log.i("group", "회비 성공")
                        onSuccess()
                    } else if (it is Response.Failure) {
                        Log.i("group", "회비 불성공")
                    }
                }
            }
        }

        //걍 입금
        fun postDeposit(groupDepositRequestDto: GroupDepositRequestDto, onSuccess: () -> Unit) {
            viewModelScope.launch {
                this@GroupAccountViewModel.run {
                    groupAccountRepository.postDeposit(groupDepositRequestDto)
                }.collect {
                    if (it is Response.Success) {
                        Log.i("group", "돈 입금성공")
                        onSuccess()
                    } else if (it is Response.Failure) {
                        Log.i("group", "돈 입금성공XXXXX")
                    }
                }
            }
        }

        //돈 출금
        fun postWithdraw(
            groupWithdrawDuesRequestDto: GroupWithdrawDuesRequestDto,
            onSuccess: () -> Unit
        ) {
            viewModelScope.launch {
                this@GroupAccountViewModel.run {
                    groupAccountRepository.postWithdrawDues(groupWithdrawDuesRequestDto)
                }.collect {
                    if (it is Response.Success) {
                        Log.i("group", "돈 출금성공")
                        onSuccess()
                    } else if (it is Response.Failure) {
                        Log.i("group", "돈 출금성공XXXXX")
                    }
                }
            }
        }
    }






