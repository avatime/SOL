package com.finance.backend.bank.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.finance.backend.bank.Account

class BankAccountRes(
        acNo : String,
        balance : Int,
        acName : String,

) {

        @JsonProperty("ac_no")
        val acNo: String = acNo

        val balance: Int = balance

        @JsonProperty("ac_name")
        val acName: String = acName
}
