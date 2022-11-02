package com.finance.android.domain.dto.response

import android.net.Uri

data class Contact(
    val contactId: Long,
    val name: String,
    val phoneNumber: List<String>,
    val avatar: String
)


