package com.finance.backend.corporation.response

import com.fasterxml.jackson.annotation.JsonProperty

class BankInfoRes(
        cpName: String,
        cpLogo: String,
        cpCode: Long
) {

    @JsonProperty("cp_name")
    val cpName: String = cpName

    @JsonProperty("cp_logo")
    val cpLogo: String = cpLogo

    @JsonProperty("cp_code")
    val cpCode: Long = cpCode
}