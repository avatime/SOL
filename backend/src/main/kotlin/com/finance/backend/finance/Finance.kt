package com.finance.backend.finance

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

class Finance(
        id: Long,
        fnName: String,
        fnCode: Int,
        fnDate : LocalDateTime
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    val id: Long = id

    @JsonProperty("fn_name")
    val fnName: String = fnName

    @JsonProperty("fn_code")
    val fnCode: Int = fnCode

    @JsonProperty("fn_close")
    val fnDate : LocalDateTime = fnDate

    val open : Int = 0

    val close : Int = 0

    val high : Int = 0

    val low : Int = 0
    
    val volume : Int = 0

    val per : Double = 0.0
}