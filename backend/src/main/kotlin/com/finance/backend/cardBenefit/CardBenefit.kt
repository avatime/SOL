package com.finance.backend.cardBenefit

import com.fasterxml.jackson.annotation.JsonProperty
import com.finance.backend.cardBenefitImg.CardBenefitImg
import com.finance.backend.cardProduct.CardProduct
import javax.persistence.*

@Entity
@Table(name = "card_benefit")
class CardBenefit(
        cardProduct: CardProduct,
        cardBenefitImg: CardBenefitImg
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    var id: Long = 0
        protected set

    @JsonProperty("cd_bf_name")
    val cdBfName: String = ""

    @JsonProperty("cd_bf_sum")
    val cdBfSum: String = ""

    @JsonProperty("cd_bf_detail")
    val cdBfDetail: String = ""

    @ManyToOne
    @JoinColumn(name ="cd_pd_code")
    val cardProduct = cardProduct

    @OneToOne
    @JsonProperty("cd_bf_img")
    val cdBfImg: CardBenefitImg = cardBenefitImg
}