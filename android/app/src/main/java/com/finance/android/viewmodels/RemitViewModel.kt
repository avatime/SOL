package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.response.BankInfoResponseDto
import com.finance.android.domain.dto.response.RecentTradeResponseDto
import com.finance.android.domain.repository.BankRepository
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.RemitRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemitViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val remitRepository: RemitRepository,
    private val bankRepository: BankRepository
) : BaseViewModel(application, baseRepository) {

    private val _recommendedAccountData = mutableStateOf<Response<MutableList<RecentTradeResponseDto>>>(Response.Loading)
    val recommendedAccountData = _recommendedAccountData

    fun getRecommendedAccountData() {
        viewModelScope.launch {
            this@RemitViewModel.run {
                remitRepository.getRecommendedAccount()
            }
                .collect {
                    _recommendedAccountData.value = it
                    //
                }
        }
    }

    //모든 은행 기업 조회
    private val _allBankData = mutableStateOf<Response<MutableList<BankInfoResponseDto>>>(Response.Loading)
    val allBankData = _allBankData
    fun getAllBankData () {
        viewModelScope.launch {
            this@RemitViewModel.run {
                bankRepository.getAllBank()
            }
                .collect {
                    _allBankData.value = it

                }
        }

    }

    fun onClickBookmark(key: Any) {
        _recommendedAccountData.value
    }
}
