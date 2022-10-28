package com.finance.backend.bank

import com.fasterxml.jackson.annotation.JsonProperty
import com.finance.backend.user.User
import java.rmi.activation.ActivationGroup_Stub
import java.time.LocalDateTime
import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "account")
class Account (
        acNo: String,
        balance: Int,
        user: User,
        acType: Int,
        acName: String,
        acPdCode: Long,
        acCpCode: Long,
        acStatus: Int,
        date: LocalDateTime

){
        @Id
        @Column
        val acNo : String = acNo

        var balance : Int = balance

        @JsonProperty("ac_type")
        var acType : Int = acType

        @JsonProperty("ac_name")
        var acName : String = acName

        @JsonProperty("ac_pd_code")
        var acPdCode : Long = acPdCode

        @JsonProperty("ac_cp_code")
        var acCpCode : Long = acCpCode

        @JsonProperty("ac_status")
        var acStatus : Int = acStatus

        @JsonProperty("ac_reg")
        var acReg : Boolean? = false

        @JsonProperty("ac_new_dt")
        var acNewDt : LocalDateTime = date

        @JsonProperty("ac_close_dt")
        var acCloseDt : LocalDateTime = date

        @JsonProperty("ac_rm_req")
        var acRmReg : Boolean? = false

        @ManyToOne
        val user: User = user

        fun withdraw(money : Int) {
                this.balance -= money
        }
}
