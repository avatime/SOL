package com.finance.backend.bookmark

import com.fasterxml.jackson.annotation.JsonProperty
import com.finance.backend.user.User
import javax.persistence.*

@Entity
@Table(name = "bookmark")
class Bookmark(
        acNo : String,
        user : User,
        bkStatus : Boolean
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    var id: Long = 0
        protected set

    @JsonProperty("ac_no")
    var acNo: String = acNo

    @JsonProperty("bk_status")
    var bkStatus: Boolean = bkStatus

    @ManyToOne
    var user: User = user

}