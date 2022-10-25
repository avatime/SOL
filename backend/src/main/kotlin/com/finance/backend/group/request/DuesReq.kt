package com.finance.backend.group.request

import com.fasterxml.jackson.annotation.JsonProperty

data class DuesReq(@JsonProperty("dues_id") val duesId : Long)
