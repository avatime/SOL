package com.finance.android.domain.dto.response

import com.finance.android.ui.components.HistoryEntity
import com.google.gson.annotations.SerializedName
import java.time.ZoneId
import java.util.*

data class PointHistoryResponseDto(
    @SerializedName("day")
    val day : Date,
    @SerializedName("name")
    val name : String,
    @SerializedName("point")
    val point : Int
) {
    fun toEntity() : HistoryEntity = HistoryEntity(day, name, point)
}
