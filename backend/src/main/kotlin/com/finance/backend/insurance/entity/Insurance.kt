package com.finance.backend.insurance.entity

import com.finance.backend.bank.Account
import com.finance.backend.insurance.response.MyInsuranceInfoDetailRes
import com.finance.backend.insurance.response.MyInsuranceInfoRes
import com.finance.backend.user.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "Insurance")
class Insurance (user : User, account : Account) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    var id: Long = 0
        protected set

    @Column
    var isRegDt : LocalDateTime = LocalDateTime.now()
        protected set

    @Column
    var isCloDt : LocalDateTime = LocalDateTime.now()
        protected set

    @Column
    var isMatDt : LocalDateTime = LocalDateTime.now()
        protected set

    @Column
    var isStatus : Int = 10
        protected set

    @Column
    var isPdCode : Long = 0
        protected set

    @Column(length = 30)
    var isName : String = ""
        protected set

    @Column
    var isReg : Boolean = false
        protected set

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user : User = user
        protected set

    @Column
    var fee : Int = 0
        protected set

    @ManyToOne
    @JoinColumn(name = "ac_no")
    var account : Account = account
        protected set

    fun registApp() {this.isReg = true}
    fun toEntity(name : String) : MyInsuranceInfoDetailRes = MyInsuranceInfoDetailRes(this.id, name, this.fee, this.user.name, this.isName, this.isReg, this.isPdCode)
}