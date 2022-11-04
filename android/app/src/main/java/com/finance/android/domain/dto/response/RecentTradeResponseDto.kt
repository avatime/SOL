package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class RecentTradeResponseDto(
    @SerializedName("ac_receive") // 보낸 사람 이름
    val acReceive: String,
    @SerializedName("ac_no") // 계좌 번호
    val acNo: String,
    @SerializedName("cp_name") // 기업 이름
    val cpName: String,
    @SerializedName("bk_status") // 북마크 여부
    val bkStatus: Boolean,
    @SerializedName("cp_logo") // 기업 여부
    val cpLogo: String,
    @SerializedName("td_dt") // 거래 일자
    val tdData: String
)
