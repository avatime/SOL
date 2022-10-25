package com.finance.android.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.repository.SampleRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val sampleRepository: SampleRepository
): ViewModel() {
    private val _data = mutableStateOf<Response<Array<Int>>>(Response.Loading)
    val data = _data

    fun getData() {
        viewModelScope.launch {
            sampleRepository.getSampleData().collect {
                _data.value = it
            }
        }
    }
}