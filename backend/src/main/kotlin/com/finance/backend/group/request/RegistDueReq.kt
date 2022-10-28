package com.finance.backend.group.request

import com.fasterxml.jackson.annotation.JsonProperty

data class RegistDueReq(
        var name : String,
        @JsonProperty("pa_id")
        var paId : Long,
        @JsonProperty("dues_val")
        var duesVal : Int,
        @JsonProperty("dues_due")
        var duesDue : String?,
        @JsonProperty("member_list")
        var memberList : List<MemberInfoReq>
)
