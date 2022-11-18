package com.finance.backend.daily.entity

import com.finance.backend.user.User
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "walk")
class Walk (
        user : User
        ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    var id: Long = 0
        protected set

    @CreatedDate
    var walkDate: LocalDateTime = LocalDateTime.now()

    @Column
    var walk : Int = 0

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user : User = user

    fun walk(walk: Int) {this.walk = walk}
    fun addWalk() {this.walk++}
}