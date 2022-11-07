package com.finance.android.domain.dto.response

import com.google.gson.annotations.SerializedName

data class ContactDto(
    @SerializedName("contact_id")
    val contactId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone_number")
    val phoneNumber: List<String>,
    @SerializedName("avatar")
    val avatar: String
)