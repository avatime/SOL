package com.finance.backend.card

import com.fasterxml.jackson.annotation.JsonProperty
import com.finance.backend.bank.Account
import com.finance.backend.user.User
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "card")
class Card (
        cdNo: String,
        cdPwd: String,
        cdMtMonth: String,
        cdMtYear: String,
        cdPdCode: Long,
        cdStatus: Int,
        cdReg: Boolean,
        user: User,
        account: Account
        ) {

    @Id
    @JsonProperty("cd_no")
    val cdNo = cdNo

    val cdPwd = cdPwd

    val cdMtMonth = cdMtMonth

    val cdMtYear = cdMtYear

    val cdPdCode = cdPdCode

    var cdStatus = cdStatus

    var cdReg = cdReg

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user = user

    @OneToOne
    val account = account

}