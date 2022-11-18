package com.finance.android.domain.dto.response

data class InsuranceDetailResponseDto (
    val id: Long, // 아이디
    val name: String, // 보험 상품 이름
    val type: String, // 보험 타입
    val detail: List<String> // 보험 설명
)