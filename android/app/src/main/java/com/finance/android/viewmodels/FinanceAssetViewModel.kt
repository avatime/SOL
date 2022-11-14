package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import com.finance.android.domain.dto.response.BankAccountResponseDto
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FinanceAssetViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository
) : BaseViewModel(application, baseRepository){
    private val financeList = mutableStateOf<Response<MutableList<BankAccountResponseDto>>>(Response.Loading)

    fun getLoadState(): Response<Unit> {
        val arr = arrayOf(financeList)

        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }
}