package com.finance.backend.remit

import com.fasterxml.jackson.annotation.JsonProperty
import com.finance.backend.bank.Account
import com.finance.backend.remit.response.RemitAvailableRes
import com.finance.backend.remit.response.RemitTokenRes
import javax.persistence.*

@Entity(name = "remit_available")
class RemitAvailable(
        token : Boolean,
        account : String,
        value : Long
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

    @Column(nullable = false)
    var account : String = account

    @Column(nullable = false)
    var value : Long = value

    fun check(){ this.token = false }

    fun toEntity() : RemitAvailableRes = RemitAvailableRes(this.tokenId, this.token)
    fun toEntity(ac : Account) : RemitTokenRes = RemitTokenRes(this.token, ac.acName, ac.acNo, ac.user.name, this.value)
}