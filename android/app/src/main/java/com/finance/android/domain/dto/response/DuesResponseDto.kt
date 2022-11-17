package com.finance.android.domain.dto.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class DuesResponseDto(
    @SerializedName("paid")
    val paid: Boolean,
    // 유저 지불 여부
    @SerializedName("dues_id")
    val duesId: Int, // 회비 아이디
    @SerializedName("dues_name")
    val duesName: String, // 회비 이름
    @SerializedName("created_at")
    val createdAt: String,
    // 생성 날짜
    @SerializedName("due_date")
    val dueDate: LocalDateTime, // 만기일 (정기납부일때만)
    @SerializedName("dues_val")
    val duesVal: Int, // 금액
    @SerializedName("paid_user")
    val paidUser: Int,
    // 회비 낸 인원수
    @SerializedName("total_user")
    val totalUSer: Int, // 전체 인원 수
    @SerializedName("creator")
    val creator: String, // 만든 사람
    @SerializedName("dues_detail_res")
    @Expose
    val duesDetailResponseDto: DuesDetailResponseDto
)
