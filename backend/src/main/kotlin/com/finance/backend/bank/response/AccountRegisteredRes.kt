package com.finance.backend.bank.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.finance.backend.card.response.CardInfoRes
import com.finance.backend.card.response.CardRes
import com.finance.backend.insurance.response.MyInsuranceInfoDetailRes
import com.finance.backend.insurance.response.MyInsuranceInfoRes

class AccountRegisteredRes(
        accountList: List<BankAccountRes>,
        insuranceList: List<MyInsuranceInfoDetailRes>,
        financeList: List<BankAccountRes>,
        cardList : List<CardRes>
) {
    @JsonProperty("account_list")
    val accountList = accountList

    @JsonProperty("insurance_list")
    val insuranceList = insuranceList

    @JsonProperty("finance_list")
    val financeList = financeList

    @JsonProperty("card_list")
    val cardList = cardList
}