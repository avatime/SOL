package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.datastore.UserStore
import com.finance.android.domain.dto.response.BankAccountResponseDto
import com.finance.android.domain.repository.*
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAssetViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val userRepository: UserRepository,
    private val bankRepository: BankRepository,
    private val cardRepository: CardRepository,
    private val insuranceRepository: InsuranceRepository,
    private val stockRepository: StockRepository
) : BaseViewModel(application, baseRepository) {
    val selectedAll = mutableStateOf(false)
    val accountList = mutableStateOf<Response<MutableList<BankAccountResponseDto>>>(Response.Loading)

    fun createAssetAndLoad() {
        viewModelScope.launch {
            createAsset {
                loadAccountList()
            }
        }
    }

    fun onClickSelectAll() {
        selectedAll.value = !selectedAll.value
    }

    private suspend fun createAsset(onSuccess: suspend () -> Unit) {
        UserStore(getApplication()).getValue(UserStore.KEY_USER_ID)
            .collect {
                this@AddAssetViewModel.run {
                    userRepository.createAsset(it)
                }.collect {
                    if (it is Response.Success) {
                        onSuccess()
                    }
                }
            }
    }

    private suspend fun loadAccountList() {
        this@AddAssetViewModel.run {
            bankRepository.getAccountList()
        }
            .collect {
                accountList.value = it
            }
    }
}
