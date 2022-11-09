package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class IsPaidResponseDto(

    @SerializedName("friend_res")
    val friendRes: MutableList<FriendResponseDto>,
    @SerializedName("status")
    val status : Boolean, //회비 납부 여부
)
