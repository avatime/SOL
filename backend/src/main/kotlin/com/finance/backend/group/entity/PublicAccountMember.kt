package com.finance.backend.group.entity

import com.finance.backend.group.response.FriendRes
import com.finance.backend.profile.Profile
import com.finance.backend.user.User
import javax.persistence.*

@Entity(name = "publicAccountMember")
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

    fun toEntity(profile: Profile?) : FriendRes = FriendRes(this.user.name, if(this.type == "회원") "" else this.type, profile?.pfImg ?: null, profile?.pfName ?: null, this.user.phone)
}