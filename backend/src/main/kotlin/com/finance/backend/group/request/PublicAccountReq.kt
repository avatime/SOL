package com.finance.backend.group.request

import com.fasterxml.jackson.annotation.JsonProperty

data class PublicAccountReq(@JsonProperty("pa_id") val paId : Long)
