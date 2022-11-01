package com.finance.android.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.response.BankInfoResponseDto
import com.finance.android.domain.dto.response.RecentTradeResponseDto
import com.finance.android.domain.repository.BankRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankViewModel @Inject constructor(
    private val bankRepository: BankRepository
) : ViewModel () {


//    private val _allBackAccountData = mutableStateOf<Response<MutableList<BankInfoResponseDto>>>(
//        Response.Loading
//    )
//
//    val allBankAccountData = _allBackAccountData
//
//    fun getAllBankAccountData () {
//        viewModelScope.launch {
//            bankRepository.getAllBankAccount().collect {
//                _allBackAccountData.value = it
//            }
//        }
//    }
}