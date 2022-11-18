package com.finance.android.domain.service

import androidx.compose.runtime.MutableState
import com.finance.android.domain.dto.request.*
import com.finance.android.domain.dto.response.DuesResponseDto
import com.finance.android.domain.dto.response.FriendResponseDto
import com.finance.android.domain.dto.response.PublicAccountResponseDto
import com.finance.android.domain.dto.response.PublicTradeResponseDto
import com.finance.android.utils.Const
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface GroupAccountService {
    @GET("${Const.API_PATH}/public")//모임 통장 조회
    suspend fun getGroupAccount(): MutableList<PublicAccountResponseDto>

    @POST("${Const.API_PATH}/public")//모임 통장 생성
    suspend fun postMakeGroupAccount(@Body createGroupAccountRequestDto: CreateGroupAccountRequestDto)

    @PUT("${Const.API_PATH}/public")// 모임 통장 제거
    suspend fun putDeleteGroupAccount(@Body groupIdRequestDto: GroupIdRequestDto)

    @POST("${Const.API_PATH}/public/trade")//입출금 내역 조회
    suspend fun postTradeHistory(@Body groupIdRequestDto: GroupIdRequestDto) : MutableList<PublicTradeResponseDto>

    @POST("${Const.API_PATH}/public/friend")// 모임 친구 조회
    suspend fun postGroupMember(@Body groupIdRequestDto: GroupIdRequestDto) : MutableList<FriendResponseDto>

    @POST("${Const.API_PATH}/public/dues")// 회비 전체 조회
    suspend fun postDuesHistory(@Body groupIdRequestDto: GroupIdRequestDto) : MutableList<DuesResponseDto>

    @POST("${Const.API_PATH}/public/dues/detail") //회비 상세 조회
    suspend fun postDuesHistoryDetail(@Body groupDuesRequestDto: GroupDuesRequestDto) : MutableList<DuesResponseDto>

    @POST("${Const.API_PATH}/public/dues/pay") // 회비 입금
    suspend fun postPayDues(@Body remitDuesRequestDto: RemitDuesRequestDto)

    @POST("${Const.API_PATH}/public/dues/regist") //회비 생성
    suspend fun postRegistDues(@Body createDuesRequestDto: CreateDuesRequestDto)

    @PUT("${Const.API_PATH}/public/dues") //회비 비활성화
    suspend fun putDeleteDues(@Body groupDuesRequestDto: GroupDuesRequestDto)

    @POST("${Const.API_PATH}/public/info") //회비 정보
    suspend fun postGroupAccountInfo(@Body groupIdRequestDto: GroupIdRequestDto) : PublicAccountResponseDto

    @POST("${Const.API_PATH}/public/withdraw") //회비 출금
    suspend fun postWithdrawDues(@Body groupWithdrawDuesRequestDto: GroupWithdrawDuesRequestDto)

    @POST("${Const.API_PATH}/public/deposit") //돈 입금
    suspend fun postDeposit(@Body groupDepositRequestDto: GroupDepositRequestDto)
}