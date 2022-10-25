package com.finance.backend.group.response

import com.fasterxml.jackson.annotation.JsonProperty

data class FriendRes(
        @JsonProperty("user_name")
        val userName : String,

        val type : String,

        @JsonProperty("pf_img")
        val pfImg : String,

        @JsonProperty("pf_name")
        val pfName : String,
)
