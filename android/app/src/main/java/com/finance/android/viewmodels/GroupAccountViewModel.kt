package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
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



}