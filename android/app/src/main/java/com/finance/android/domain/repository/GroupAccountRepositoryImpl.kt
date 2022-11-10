package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.CreateDuesRequestDto
import com.finance.android.domain.dto.request.CreateGroupAccountRequestDto
import com.finance.android.domain.dto.request.GroupDuesRequestDto
import com.finance.android.domain.dto.request.GroupIdRequestDto
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
        return groupAccountService.postGroupMember(groupIdRequestDto)
    }

    override suspend fun postDuesHistory(groupIdRequestDto: GroupIdRequestDto): MutableList<DuesResponseDto> {
        return groupAccountService.postDuesHistory(groupIdRequestDto)
    }

    override suspend fun postDuesHistoryDetail(groupDuesRequestDto: GroupDuesRequestDto): MutableList<DuesResponseDto> {
        return groupAccountService.postDuesHistoryDetail(groupDuesRequestDto)
    }

    override suspend fun postPayDues(remitDuesResponseDto: DuesResponseDto) {
        return groupAccountService.postPayDues(remitDuesResponseDto)
    }

    override suspend fun postRegistDues(createDuesRequestDto: CreateDuesRequestDto) {
        return groupAccountService.postRegistDues(createDuesRequestDto)
    }

    override suspend fun putDeleteDues(groupDuesRequestDto: GroupDuesRequestDto) {
        return groupAccountService.putDeleteDues(groupDuesRequestDto)
    }
}