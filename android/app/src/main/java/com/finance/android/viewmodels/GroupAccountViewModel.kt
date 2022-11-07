package com.finance.android.viewmodels

import android.app.Application
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.GroupAccountRepository
import com.finance.android.domain.repository.StockRepository
import javax.inject.Inject

class GroupAccountViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val groupAccountRepository: GroupAccountRepository
) : BaseViewModel(application, baseRepository){

}