package com.finance.backend.cardProduct

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
@Table(name = "card_product")
class CardProduct {

    @Id
    @JsonProperty("cd_pd_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    var cdPdCode: Long = 0
        protected set

    @JsonProperty("cd_name")
    val cdName = ""

    @JsonProperty("cp_code")
    val cpCode = 0

    @JsonProperty("cd_type")
    val cdType = 0

    @JsonProperty("cd_img")
    val cdImg = ""

    @JsonProperty("cd_perm")
    val cdPerm = ""

    @JsonProperty("cd_fee")
    val cdFee = ""

    @JsonProperty("cd_link")
    val cdLink = ""
}