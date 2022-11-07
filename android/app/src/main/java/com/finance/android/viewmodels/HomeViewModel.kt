package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.response.*
import com.finance.android.domain.repository.BankRepository
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.CardRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val bankRepository: BankRepository,
) : BaseViewModel(application, baseRepository) {
    val mainData = mutableStateOf<Response<AccountRegisteredResponseDto>>(Response.Loading)

    fun load() {
        viewModelScope.launch {
            loadList()
        }
    }

    fun getLoadState(): Response<Unit> {
        val arr = arrayOf(mainData)

        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }

    private suspend fun loadList() {
        this@HomeViewModel.run {
            bankRepository.getAllMainAccount()
        }
            .collect {
                mainData.value = it
            }
    }

}