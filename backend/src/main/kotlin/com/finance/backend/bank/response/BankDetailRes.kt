package com.finance.backend.bank.response

import com.fasterxml.jackson.annotation.JsonProperty

class BankDetailRes(
        bankAccountRes : BankAccountRes,
        bankTradeList : ArrayList<BankTradeRes>
) {
    @JsonProperty("bank_account_res")
    val bankAccountRes: BankAccountRes = bankAccountRes

    @JsonProperty("bank_trade_list")
    val bankTradeList: ArrayList<BankTradeRes> = bankTradeList
}