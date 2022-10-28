package com.finance.backend.insurance.entity

import javax.persistence.*

@Entity(name = "Is_detail")
class IsDetail(isPd: IsProduct) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    var id: Long = 0
        protected set

    @ManyToOne
    @JoinColumn(name = "is_pd_id")
    var isPd : IsProduct = isPd
        protected set
    @Column(columnDefinition = "TEXT")
    var isPdDetail : String = ""
        protected set
}