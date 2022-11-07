package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class AccountRegisteredResponseDto (
    @SerializedName("account_list")
    val accountList : MutableList<BankAccountResponseDto>,
    @SerializedName("insurance_list")
    val insuranceList : MutableList<InsuranceInfoResponseDto>,
    @SerializedName("finance_list")
    val financeList : MutableList<BankAccountResponseDto>,
    @SerializedName("card_list")
    val cardList : MutableList<CardInfoResponseDto>
)