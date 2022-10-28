package com.finance.backend.insurance.request

import com.fasterxml.jackson.annotation.JsonProperty

data class InsuranceReq(@JsonProperty("is_id") val isId : Long)
