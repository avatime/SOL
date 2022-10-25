package com.finance.backend.corporation

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
@Table(name="corporation")
class Corporation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("cp_code")
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    var cpCode: Long = 0

    @JsonProperty("cp_name")
    var cpName: String = ""

    @JsonProperty("cp_logo")
    var cpLogo: String = ""
}