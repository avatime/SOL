package com.finance.android.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.request.AccountNumberDto
import com.finance.android.domain.dto.request.CheckAccountRequestDto
import com.finance.android.domain.dto.request.RemitInfoRequestDto
import com.finance.android.domain.dto.request.RemitPhoneRequestDto
import com.finance.android.domain.dto.response.BankInfoResponseDto
import com.finance.android.domain.dto.response.RecentMyTradeResponseDto
import com.finance.android.domain.dto.response.RecentTradeResponseDto
import com.finance.android.domain.repository.BankRepository
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.RemitRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemitViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    savedStateHandle: SavedStateHandle,
    private val remitRepository: RemitRepository,
    private val bankRepository: BankRepository
) : BaseViewModel(application, baseRepository) {
    val accountName = savedStateHandle.get<String>("accountName")!! //acName
    val accountNumber = savedStateHandle.get<String>("accountNumber")!! //ac_send
    val moneyValue = mutableStateOf("")
    val balance = savedStateHandle.get<Int>("balance")
    val enabled = mutableStateOf(false)
    val requestRemit = mutableStateOf(false)
    var cpCode = mutableStateOf(0)

    private val _recommendedAccountData =
        mutableStateOf<Response<MutableList<RecentTradeResponseDto>>>(Response.Loading)
    val recommendedAccountData = _recommendedAccountData

    fun getRecommendedAccountData() {
        viewModelScope.launch {
            this@RemitViewModel.run {
                remitRepository.getRecommendedAccount()
            }
                .collect {
                    _recommendedAccountData.value = it

                }
        }
    }

    //송금 내 계좌 조회
    private val _recentMyAccountData = mutableStateOf<Response<MutableList<RecentMyTradeResponseDto>>>(Response.Loading)
    val recentMyAccountData = _recentMyAccountData

    fun getRecentMyAccountData() {
        viewModelScope.launch {
            this@RemitViewModel.run {
                bankRepository.getRecentMyAccount()
            }.collect {
                _recentMyAccountData.value = it
            }
        }
    }


    //모든 은행 기업 조회
    private val _allBankData =
        mutableStateOf<Response<MutableList<BankInfoResponseDto>>>(Response.Loading)
    val allBankData = _allBankData
    fun getAllBankData() {
        viewModelScope.launch {
            this@RemitViewModel.run {
                bankRepository.getAllBank()
            }
                .collect {
                    _allBankData.value = it

                }
        }

    }

    //모든 증권 기업 조회
    private val _allStockCpData =
        mutableStateOf<Response<MutableList<BankInfoResponseDto>>>(Response.Loading)
    val allStockCpData = _allStockCpData
    fun getAllStockCpData() {
        viewModelScope.launch {
            this@RemitViewModel.run {
                bankRepository.getAllStockCp()
            }
                .collect {
                    _allStockCpData.value = it
                }
        }
    }

    //계좌 체크
    var isRightAccount = mutableStateOf(false)
    var validRecieveAccountNumber = mutableStateOf("0")
    var validRecieveBankName = mutableStateOf("0")

    fun checkRightAccount(acNo: String, cpCode: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            this@RemitViewModel.run {
                bankRepository.checkAccount(CheckAccountRequestDto(acNo, cpCode))
            }.collect {
                if (it is Response.Success) {
                    if (it.data.userName.isEmpty()) {

                    } else {
                        Log.i("test", "Success")
                        validRecieveAccountNumber.value = acNo
                        validRecieveBankName.value = selectedReceiveBank.value!!.cpName.toString()
                        onSuccess()
                    }


                }
            }
        }
    }

    //계좌번호로 송금하기
    fun remitFromAccount(
        value: Int, receive: String,
        send: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            this@RemitViewModel.run {
                remitRepository.postRemitToAccount(
                    RemitInfoRequestDto(
                        acTag = selectedReceiveBank.value!!.cpName,
                        acReceive = validRecieveAccountNumber.value,
                        acSend = accountNumber,
                        acName = accountName,
                        value = value,
                        receive = receive,
                        send = send
                    )
                )
            }.collect {
                if (it is Response.Success) {
                    Log.i("remitAccount", "갓찬영")
                    onSuccess()
                } else if (it is Response.Failure) {
                    Log.i("remitAccount", "김챤챤영 ㅡㅡ")
                }
            }
        }

    }

    val phoneNum = mutableStateOf("")

    //전화번호 가져오기
    fun onClickContact(phone: String) {
        phoneNum.value = phone

    }

    //전화번호로 송금하기
    fun remitFromPhone(
        value: Int, receive: String,
        send: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            this@RemitViewModel.run {
                remitRepository.postRemitToPhone(
                    RemitPhoneRequestDto(
                        acSend = accountNumber,
                        acName = accountName,
                        value = value,
                        receive = receive,
                        send = send,
                        phone = phoneNum.value.replace("-", ""),
                    )
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

    //북마크
    fun onClickAccountBookmark (accountNumber : String) {
        viewModelScope.launch {
            this@RemitViewModel.run{
                remitRepository.putRemitBookmark(accountNumberDto = AccountNumberDto(accountNumber))
            }.collect {
                if (it is Response.Success) {
                    Log.i("remitAccount", "북마크 갓찬영")

                } else if (it is Response.Failure) {
                    Log.i("remitAccount", "북마크 김챤챤영 ㅡㅡ")
                }
            }
        }
    }



    fun onClickAccount(key: Any) {
        _recommendedAccountData.value
    }

    val selectedReceiveBank = mutableStateOf<BankInfoResponseDto?>(null)
    fun onClickReceiveBank(bankInfoResponseDto: BankInfoResponseDto) {
        selectedReceiveBank.value = bankInfoResponseDto;
    }
}
