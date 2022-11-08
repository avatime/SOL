package com.finance.android.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.GroupAccountRepository
import com.finance.android.domain.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupAccountViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val groupAccountRepository: GroupAccountRepository
) : BaseViewModel(application, baseRepository){

    val name = mutableStateOf("")
}