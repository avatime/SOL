    package com.finance.android.domain.service

import com.finance.android.domain.dto.request.StockAccountNumberDto
import com.finance.android.domain.dto.response.BankAccountResponseDto
import com.finance.android.domain.dto.response.FinanceDetailResponseDto
import com.finance.android.domain.dto.response.FinanceResponseDto
import com.finance.android.utils.Const
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface StockService {
    @GET("${Const.API_PATH}/finance/asset/all")
    suspend fun getStockAccountList(): MutableList<BankAccountResponseDto>

    @PUT("${Const.API_PATH}/finance/asset")
    suspend fun putRegisterStockAccount(@Body stockAccountNumberDtoArray: Array<StockAccountNumberDto>)

    @GET("${Const.DATA_PATH}/finance")
    suspend fun getFinanceList(): Array<FinanceResponseDto>

    @GET("${Const.DATA_PATH}/finance/{fn_name}")
    suspend fun getFinanceDetailList(@Path("fn_name")fnName: String) : Array<FinanceDetailResponseDto>

    @GET("${Const.API_PATH}/finance/asset")
    suspend fun getMyFinanceList(): MutableList<BankAccountResponseDto>
}
