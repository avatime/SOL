package com.finance.backend.group.entity

import com.finance.backend.user.User
import javax.persistence.*

class PublicAccountMember(
        publicAccount: PublicAccount,
        user : User,
        type : String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    var id: Long = 0
        protected set

    @ManyToOne
    @JoinColumn(name = "pa_id")
    var publicAccount : PublicAccount = publicAccount

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user : User = user

    @Column
    var type : String = type
}