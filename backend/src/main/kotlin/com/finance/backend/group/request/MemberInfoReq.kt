package com.finance.backend.group.request

import com.fasterxml.jackson.annotation.JsonProperty

data class MemberInfoReq(val name : String, @JsonProperty("member_list") val phone : String)
