package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body

data class DuesDetailResponseDto(
    @SerializedName("dues_name")
    val duesName : String,
    @SerializedName("dues_val")
    val duesVal : Int,
    @SerializedName("can_delete")
    val canDelete : Boolean,
    @SerializedName("check")
    val check : MutableList<IsPaidResponseDto>

)
