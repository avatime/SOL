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
    var bkStatus: Boolean,
    @SerializedName("cp_logo") // 기업 여부
    val cpLogo: String,
    @SerializedName("td_dt") // 거래 일자
    val tdData: String
){
    override fun equals(other: Any?): Boolean {
         return false
    }

    override fun hashCode(): Int {
        var result = acReceive.hashCode()
        result = 31 * result + acNo.hashCode()
        result = 31 * result + cpName.hashCode()
        result = 31 * result + bkStatus.hashCode()
        result = 31 * result + cpLogo.hashCode()
        result = 31 * result + tdData.hashCode()
        return result
    }
}
