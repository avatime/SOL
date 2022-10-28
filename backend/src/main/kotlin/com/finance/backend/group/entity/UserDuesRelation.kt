package com.finance.backend.group.entity

import com.finance.backend.group.response.FriendRes
import com.finance.backend.group.response.IsPaidRes
import com.finance.backend.profile.Profile
import com.finance.backend.user.User
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "user_dues_relation")
class UserDuesRelation(
        dues: Dues,
        user : User
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    var id: Long = 0
        protected set

    var dueDate: LocalDateTime? = null

    @Column
    var status : Boolean = false
        protected set

    @ManyToOne
    @JoinColumn(name = "dues_id")
    var dues : Dues = dues

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user : User = user

    fun paid() {
        this.dueDate = LocalDateTime.now()
        this.status = true
    }

    fun toEntity(profile : Profile) : IsPaidRes = IsPaidRes(FriendRes(this.user.name, this.user.type, profile.pfImg, profile.pfName), this.status)
}