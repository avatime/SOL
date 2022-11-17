package com.finance.android.domain.dto.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.http.Body

data class DuesDetailResponseDto(
    @SerializedName("dues_name")
    @Expose
    val duesName : String,
    @SerializedName("dues_val")
    @Expose
    val duesVal : Int,
    @SerializedName("can_delete")
    @Expose
    val canDelete : Boolean,
    @SerializedName("check")
    @Expose
    val check : MutableList<IsPaidResponseDto>

)
