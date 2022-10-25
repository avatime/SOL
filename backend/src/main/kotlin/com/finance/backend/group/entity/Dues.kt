package com.finance.backend.group.entity

import com.finance.backend.util.Timestamped
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "dues")
class Dues(
        publicAccount: PublicAccount,
        duesName : String,
        duesVal : Int,
        category: Int = 0,
        duesDue : LocalDateTime?
) : Timestamped() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    var id: Long = 0
        protected set

    @Column
    var duesName : String = duesName
    @Column
    var duesVal : Int = duesVal
    @Column
    var duesDue : LocalDateTime? = duesDue
    @Column
    var category : Int = category

    @ManyToOne
    @JoinColumn(name = "pa_id")
    var publicAccount : PublicAccount = publicAccount
}