package com.finance.backend.accountProduct

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
@Table(name = "account_product")
class AccountProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("ac_pd_code")
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    var acPdCode: Long = 0

    @JsonProperty("cp_code")
    var cpCode : Int = 0

    @JsonProperty("ac_pd_name")
    var acPdName : String = ""

    @JsonProperty("ac_pd_type")
    var acPdType : String = ""

    var interest: Double = 0.0

    var period: Int = 0

    var maturity: Int = 0
}