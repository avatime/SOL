package com.finance.android.domain.dto.request

import com.google.gson.annotations.SerializedName

data class CreateDuesRequestDto(
    @SerializedName("name")
    val name : String,

    @SerializedName("pa_id")
    val paId : Int,

    @SerializedName("dues_val")
    val duesVal : Int,

    @SerializedName("dues_due")
    val duesDue : String,

    @SerializedName("member_list")
    val memberList : List<MemberRequestDto>
)
