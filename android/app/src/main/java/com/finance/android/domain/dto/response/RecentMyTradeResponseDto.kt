package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class RecentMyTradeResponseDto(
    @SerializedName("ac_name")
    val acName: String, //계좌이름
    @SerializedName("ac_no")
    val acNo: String, //계좌번호
    @SerializedName("cp_name")
    val cpName: String,//기업 이름
    @SerializedName("bk_status")
    var bkStatus: Boolean, //북마크 여부
    @SerializedName("cp_logo")
    val cpLogo: String, //기업로고
) {
    override fun equals(other: Any?): Boolean {
        return false
    }

    override fun hashCode(): Int {
        var result = acName.hashCode()
        result = 31 * result + acNo.hashCode()
        result = 31 * result + cpName.hashCode()
        result = 31 * result + bkStatus.hashCode()
        result = 31 * result + cpLogo.hashCode()
        return result
    }
}
