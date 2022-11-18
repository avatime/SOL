package com.finance.backend.data.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.finance.backend.bank.Account
import com.finance.backend.user.User
import com.finance.backend.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

class AccountReq(
        acNo: String,
        balance: Long,
        user: String,
        acType: Int,
        acName: String,
        acPdCode: Long,
        acCpCode: Long,
        acStatus: Int,
){
    @JsonProperty("ac_no")
    val acNo = acNo

    val balance : Long = balance

    @JsonProperty("ac_type")
    val acType : Int = acType

    @JsonProperty("ac_name")
    val acName : String = acName

    @JsonProperty("ac_pd_code")
    val acPdCode : Long = acPdCode

    @JsonProperty("ac_cp_code")
    val acCpCode : Long = acCpCode

    @JsonProperty("ac_status")
    val acStatus : Int = acStatus

    val user: String = user

}