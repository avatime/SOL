package com.finance.backend.group.request

import com.fasterxml.jackson.annotation.JsonProperty

data class RegistPublicAccountReq(val name : String, @JsonProperty("member_list") val memberList : List<MemberInfoReq>?)
