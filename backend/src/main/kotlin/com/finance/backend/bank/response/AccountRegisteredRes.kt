package com.finance.backend.bank.response

import com.fasterxml.jackson.annotation.JsonProperty

class AccountRegisteredRes(
        accountList: List<BankAccountRes>,
        insuranceList: List<String>,
        financeList: List<BankAccountRes>
) {
    @JsonProperty("account_list")
    val accountList = accountList

    @JsonProperty("insurance_list")
    val insuranceList = insuranceList

    @JsonProperty("finance_list")
    val financeList = financeList
}