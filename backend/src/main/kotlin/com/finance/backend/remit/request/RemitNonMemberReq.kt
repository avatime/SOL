package com.finance.backend.remit.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.finance.backend.remit.response.RemitAvailableRes

data class RemitNonMemberReq(
        @JsonProperty("remit_info_req")
        val remitInfoReq: RemitInfoReq,

        @JsonProperty("remit_available_res")
        val remitAvailableRes: RemitAvailableRes

)
