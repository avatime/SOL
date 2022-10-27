package com.finance.backend.finance

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

class finance(
        id: Long,
        fnName: String,
        fnCode: Int,
        fnClos: Int,
        fnPer: Double

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
    val fnClose: Int = fnClos

    @JsonProperty("fn_per")
    val fnPer: Double = fnPer
}