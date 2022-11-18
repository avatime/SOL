package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.response.InsuranceDetailResponseDto
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.InsuranceRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsuranceDetailViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val insuranceRepository: InsuranceRepository
) : BaseViewModel(application, baseRepository) {

    val insuranceDetail = mutableStateOf<Response<InsuranceDetailResponseDto>>(Response.Loading)
    fun load(id: Int) {
        viewModelScope.launch {
            loadInsuranceDetail(id)
        }
    }

    fun getLoadState(): Response<Unit> {
        val arr = arrayOf(insuranceDetail)
        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }

    private suspend fun loadInsuranceDetail(id: Int) {
        this@InsuranceDetailViewModel.run {
            insuranceRepository.getInsuranceDetail(id)
        }
            .collect{
                insuranceDetail.value = it
            }
    }
}