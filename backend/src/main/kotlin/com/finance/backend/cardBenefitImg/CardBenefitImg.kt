package com.finance.backend.cardBenefitImg

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
@Table(name = "card_benefit_img")
class CardBenefitImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    var id: Long = 0
        protected set

    @JsonProperty("cd_bf_img")
    val cdBfImg: String = ""
}