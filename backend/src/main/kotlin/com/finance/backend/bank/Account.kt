package com.finance.backend.bank

import com.fasterxml.jackson.annotation.JsonProperty
import com.finance.backend.user.User
import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "account")
data class Account (
        @ManyToOne
        val user: User,

        val date : Date,

        ){
        @Id
        @Column
        val acNo : String = ""

        var balance : Int = 0
                protected set

        @JsonProperty("ac_type")
        var acType : Int = 1
                protected set

        @JsonProperty("ac_name")
        var acName : String = ""
                protected set

        @JsonProperty("ac_pd_code")
        var acPdCode : Int = 0
                protected set

        @JsonProperty("ac_cp_code")
        var acCpCode : Int = 0
                protected set

        @JsonProperty("ac_status")
        var acStatus : Int = 0
                protected set

        @JsonProperty("ac_reg")
        var acReg : Boolean? = false

        @JsonProperty("ac_new_dt")
        var acNewDt : Date = date
                protected set

        @JsonProperty("ac_close_dt")
        var acCloseDt : Date = date
                protected set

        @JsonProperty("ac_rm_req")
        var acRmReg : Boolean? = false
                protected set
}
