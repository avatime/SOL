package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class CreateGroupAccountRequestDto(
    @SerializedName("name")
    val name : String,

    @SerializedName("member_list")
    val memberList : List<MemberRequestDto>
)
