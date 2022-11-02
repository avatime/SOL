package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.DailyRepository
import com.finance.android.domain.repository.SampleRepository
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val dailyRepository: DailyRepository,
) : BaseViewModel(application, baseRepository) {

    fun createAttendanceAndLoad() {
        viewModelScope.launch {
            createAttendance {
                println("이거 되나?")
            }
        }
    }

    private suspend fun createAttendance(onSuccess: suspend () -> Unit) {
        this@DailyViewModel.run {
            dailyRepository.test()
        }.collect { res ->
            if (res is Response.Success) {
                onSuccess()
            }
        }
    }
}
