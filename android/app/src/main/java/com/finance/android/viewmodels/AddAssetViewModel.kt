package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.datastore.UserStore
import com.finance.android.domain.dto.request.CreateAssetRequestDto
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

    fun getLoadState(): Response<Unit> {
        val arr = arrayOf(accountList)

        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }

    fun onClickSelectAll() {
        selectedAll.value = !selectedAll.value
    }

    private suspend fun createAsset(onSuccess: suspend () -> Unit) {
        UserStore(getApplication()).getValue(UserStore.KEY_PHONE_NUMBER)
            .collect {
                val createAssetRequestDto = CreateAssetRequestDto(
                    phoneNumber = it
                )
                this@AddAssetViewModel.run {
                    userRepository.createAsset(createAssetRequestDto)
                }
                    .collect { res ->
                        if (res is Response.Success) {
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
