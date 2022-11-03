package com.finance.android.domain.dto.response

import androidx.compose.runtime.MutableState
import com.finance.android.utils.Response
import com.google.gson.annotations.SerializedName

data class UserProfileResponseDto(
    val username: String,
    @SerializedName("profile_name")
    val profileName: String,
    @SerializedName("profile_url")
    val profileUrl: String,
    val point: Int,
)
