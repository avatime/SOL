package com.finance.android.domain.repository

import com.finance.android.domain.dto.request.CreateDuesRequestDto
import com.finance.android.domain.dto.request.CreateGroupAccountRequestDto
import com.finance.android.domain.dto.request.GroupDuesRequestDto
import com.finance.android.domain.dto.request.GroupIdRequestDto
import com.finance.android.domain.dto.response.DuesResponseDto
import com.finance.android.domain.dto.response.FriendResponseDto
import com.finance.android.domain.dto.response.PublicAccountResponseDto
import com.finance.android.domain.dto.response.PublicTradeResponseDto
import retrofit2.http.Body

interface GroupAccountRepository {
    suspend fun getGroupAccount(): MutableList<PublicAccountResponseDto>
    suspend fun postMakeGroupAccount(@Body createGroupAccountRequestDto: CreateGroupAccountRequestDto)
    suspend fun putDeleteGroupAccount(@Body groupIdRequestDto: GroupIdRequestDto)
    suspend fun postTradeHistory(@Body groupIdRequestDto: GroupIdRequestDto) : MutableList<PublicTradeResponseDto>
    suspend fun postGroupMember(@Body groupIdRequestDto: GroupIdRequestDto) : MutableList<FriendResponseDto>
    suspend fun postDuesHistory(@Body groupIdRequestDto: GroupIdRequestDto) : MutableList<DuesResponseDto>
    suspend fun postDuesHistoryDetail(@Body groupDuesRequestDto: GroupDuesRequestDto) : MutableList<DuesResponseDto>
    suspend fun postPayDues(@Body remitDuesResponseDto: DuesResponseDto)
    suspend fun postRegistDues(@Body createDuesRequestDto: CreateDuesRequestDto)
    suspend fun putDeleteDues(@Body groupDuesRequestDto: GroupDuesRequestDto)
}