package com.finance.android.domain.dto.response

import com.finance.android.ui.components.HistoryEntity
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class PublicTradeResponseDto(
    @SerializedName("td_dt")
    val tdDt: LocalDateTime, //거래 일자
    @SerializedName("td_val")
    val tdVal: Int,//거래 금액
    @SerializedName("dues_name")
    val duesName: String,//회비 이름
    @SerializedName("user_name")
    val userName: String,//거래한 사람 이름
    @SerializedName("td_type")
    val tdType: String, //입금유형
    @SerializedName("type")
    val type: String, //직책

) {
    fun toEntity(): HistoryEntity = HistoryEntity(tdDt, userName, tdVal)
}
