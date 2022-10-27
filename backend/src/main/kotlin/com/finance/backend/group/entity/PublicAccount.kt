package com.finance.backend.group.entity

import com.finance.backend.group.response.PublicAccountRes
import com.finance.backend.util.Timestamped
import javax.persistence.*

@Entity(name = "publicAccount")
class PublicAccount(
        paName : String,
) : Timestamped() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    var id: Long = 0
        protected set

    @Column
    var paName : String = paName

    @Column(columnDefinition = "INT UNSIGNED")
    var paVal : Long = 0
        protected set

    @Column
    var paStatus : Int = 10
        protected set

    fun addPaVal(value : Int) {this.paVal += value}
    fun terminate() {this.paStatus = 99}
    fun toEntity() : PublicAccountRes = PublicAccountRes(this.id, this.paName, this.paVal)

}