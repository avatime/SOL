package com.finance.android.domain.repository

import com.finance.android.domain.service.GroupAccountService
import javax.inject.Inject

class GroupAccountRepositoryImpl @Inject constructor(
    private val groupAccountService: GroupAccountService
) : GroupAccountRepository {
}