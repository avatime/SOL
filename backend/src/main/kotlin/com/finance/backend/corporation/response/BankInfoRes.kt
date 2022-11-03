package com.finance.backend.corporation.response

import com.fasterxml.jackson.annotation.JsonProperty

class BankInfoRes(
        cpName: String,
        cpLogo: String
) {

    @JsonProperty("cp_name")
    val cpName: String = cpName

    @JsonProperty("cp_logo")
    val cpLogo: String = cpLogo
}