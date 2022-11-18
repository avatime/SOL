package com.finance.android.domain.dto.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FriendResponseDto(
    var id: Int? = null,
    @SerializedName("user_name")
    @Expose
    val userName : String, //친구 이름
    @SerializedName("type")
    @Expose
    val type : String, //유저타입(회원, 비회원)
    @SerializedName("pf_img")
    @Expose
    val pfImg : String,//프로필이미지
    @SerializedName("pf_name")
    @Expose
    val pfName : String, //프로필 이름
    @SerializedName("phone")
    @Expose
    val phone : String // 폰 번호
)
