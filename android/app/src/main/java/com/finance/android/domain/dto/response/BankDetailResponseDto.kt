package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class BankDetailResponseDto(
    @SerializedName("bank_account_res")
    val bankAccountRes : MutableList<BankAccountResponseDto>,
    @SerializedName("bank_trade_res")
    val bankTradeRes : MutableList<BankTradeResponseDto>

)
