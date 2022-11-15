package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class FriendResponseDto(
    var id: Int? = null,
    @SerializedName("user_name")
    val userName : String, //친구 이름
    @SerializedName("type")
    val type : String, //유저타입(회원, 비회원)
    @SerializedName("pf_img")
    val pfImg : String,//프로필이미지
    @SerializedName("pf_name")
    val pfName : String, //프로필 이름
    @SerializedName("phone")
    val phone : String // 폰 번호
)
