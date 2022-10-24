package com.finance.backend.point

import com.finance.backend.point.response.PointDao
import com.finance.backend.user.User
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*

class Point(
        user : User,
        point : Int,
        name : String = "포인트 적립"
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    var id: Long = 0
        protected set

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user : User = user

    @CreatedDate
    var date: LocalDateTime = LocalDateTime.now()

    @Column
    var point : Int = point

    @Column
    var name : String = name

    fun toEntity() : PointDao = PointDao(this.date, this.point, this.name)
}