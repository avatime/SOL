package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class AccountRegisteredResponseDto (
    @SerializedName("account_list")
    val accountList : MutableList<BankAccountResponseDto>,
    @SerializedName("insurance_list")
    val insuranceList : MutableList<MyInsuranceInfoDetailResponseDto>,
    @SerializedName("finance_list")
    val financeList : MutableList<BankAccountResponseDto>
)