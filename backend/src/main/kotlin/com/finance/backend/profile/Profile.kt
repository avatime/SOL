package com.finance.backend.profile

import javax.persistence.*

@Entity(name = "profile")
class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    var pfId: Long = 0
        protected set

    @Column(nullable = false)
    var pfName: String = ""
        protected set

    @Column(nullable = false)
    var pfImg: String = ""
        protected set
}