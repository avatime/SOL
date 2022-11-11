package com.finance.backend.group.entity

import com.finance.backend.group.response.DuesRes
import com.finance.backend.util.Timestamped
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "dues")
class Dues(
        publicAccount: PublicAccount,
        duesName: String,
        duesVal: Long,
        category: Int = 1,
        duesDue: LocalDateTime?,
        creator : UUID
) : Timestamped() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    var id: Long = 0
        protected set

    @Column
    var duesName : String = duesName
        protected set
    @Column
    var duesVal : Long = duesVal
        protected set
    @Column
    var duesDue : LocalDateTime? = duesDue
        protected set
    @Column
    var category : Int = category
        protected set
    @Column
    var status : Int = 10
        protected set

    @Column
    var creator : UUID = creator
        protected set

    @ManyToOne
    @JoinColumn(name = "pa_id")
    var publicAccount : PublicAccount = publicAccount

    fun disable() {this.status = 99}
    fun toEntity(paid: Boolean, num:Int, total: Int, user : String) : DuesRes = DuesRes(paid, this.duesName, this.createdAt, this.duesDue, this.duesVal, num, total, user, this.id)
}