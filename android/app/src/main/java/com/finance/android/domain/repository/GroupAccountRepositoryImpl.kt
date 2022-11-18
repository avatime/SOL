package com.finance.android.domain.repository

import androidx.compose.runtime.MutableState
import com.finance.android.domain.dto.request.*
import com.finance.android.domain.dto.response.DuesResponseDto
import com.finance.android.domain.dto.response.FriendResponseDto
import com.finance.android.domain.dto.response.PublicAccountResponseDto
import com.finance.android.domain.dto.response.PublicTradeResponseDto
import com.finance.android.domain.service.GroupAccountService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupAccountRepositoryImpl @Inject constructor(
    private val groupAccountService: GroupAccountService
) : GroupAccountRepository {
    override suspend fun getGroupAccount(): MutableList<PublicAccountResponseDto> {
        return groupAccountService.getGroupAccount()
    }

    override suspend fun postMakeGroupAccount(createGroupAccountRequestDto: CreateGroupAccountRequestDto) {
        return groupAccountService.postMakeGroupAccount(createGroupAccountRequestDto)
    }

    override suspend fun putDeleteGroupAccount(groupIdRequestDto: GroupIdRequestDto) {
        return groupAccountService.putDeleteGroupAccount(groupIdRequestDto)
    }

    override suspend fun postTradeHistory(groupIdRequestDto: GroupIdRequestDto): MutableList<PublicTradeResponseDto> {
        return groupAccountService.postTradeHistory(groupIdRequestDto)
    }

    override suspend fun postGroupMember(groupIdRequestDto: GroupIdRequestDto): MutableList<FriendResponseDto> {
        return groupAccountService.postGroupMember(groupIdRequestDto).apply {
            forEachIndexed { index, friendResponseDto -> friendResponseDto.id = index  }
        }
    }

    override suspend fun postDuesHistory(groupIdRequestDto: GroupIdRequestDto): MutableList<DuesResponseDto> {
        return groupAccountService.postDuesHistory(groupIdRequestDto)
    }

    override suspend fun postDuesHistoryDetail(groupDuesRequestDto: GroupDuesRequestDto): MutableList<DuesResponseDto> {
        return groupAccountService.postDuesHistoryDetail(groupDuesRequestDto)
    }

    override suspend fun postPayDues(remitDuesRequestDto: RemitDuesRequestDto) {
        return groupAccountService.postPayDues(remitDuesRequestDto)
    }


    override suspend fun postRegistDues(createDuesRequestDto: CreateDuesRequestDto) {
        return groupAccountService.postRegistDues(createDuesRequestDto)
    }

    override suspend fun putDeleteDues(groupDuesRequestDto: GroupDuesRequestDto) {
        return groupAccountService.putDeleteDues(groupDuesRequestDto)
    }

    override suspend fun postGroupAccountInfo(groupIdRequestDto: GroupIdRequestDto): PublicAccountResponseDto {
        return groupAccountService.postGroupAccountInfo(groupIdRequestDto)
    }

    override suspend fun postDeposit(groupDepositRequestDto: GroupDepositRequestDto) {
        return groupAccountService.postDeposit(groupDepositRequestDto)
    }

    override suspend fun postWithdrawDues(groupWithdrawDuesRequestDto: GroupWithdrawDuesRequestDto) {
       return groupAccountService.postWithdrawDues(groupWithdrawDuesRequestDto)
    }
}