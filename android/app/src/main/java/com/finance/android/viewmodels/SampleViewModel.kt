package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.SampleRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val sampleRepository: SampleRepository
) : BaseViewModel(application, baseRepository) {
    private val _data = mutableStateOf<Response<Array<Int>>>(Response.Loading)
    val data = _data

    fun getData() {
        viewModelScope.launch {
            this@SampleViewModel.run {
                sampleRepository.getSampleData()
            }
                .collect {
                    _data.value = it
                }
        }
    }
}
