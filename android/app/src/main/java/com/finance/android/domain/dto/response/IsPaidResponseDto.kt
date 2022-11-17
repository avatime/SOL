package com.finance.android.domain.dto.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class IsPaidResponseDto(

    @SerializedName("friend_res")
    @Expose
    val friendRes: FriendResponseDto,
    @SerializedName("status")
    @Expose
    val status : Boolean, //회비 납부 여부
)
