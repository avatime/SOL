package com.finance.backend.remit

import com.fasterxml.jackson.annotation.JsonProperty
import com.finance.backend.remit.response.RemitAvailableRes
import javax.persistence.*

@Entity(name = "remit_available")
class RemitAvailable(
        token : Boolean
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    @JsonProperty("token_id")
    var tokenId: Long = 0
        protected set

    @Column(nullable = false)
    var token = token

    fun check(){ this.token = false }

    fun toEntity() : RemitAvailableRes = RemitAvailableRes(this.tokenId, this.token)
}