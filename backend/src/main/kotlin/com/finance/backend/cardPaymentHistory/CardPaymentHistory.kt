package com.finance.backend.cardPaymentHistory

import com.fasterxml.jackson.annotation.JsonProperty
import com.finance.backend.card.Card
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "card_payment_history")
class CardPaymentHistory(
        card: Card
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    var id: Long = 0
        protected set

    @JsonProperty("cd_py_dt")
    val cdPyDt: LocalDateTime = LocalDateTime.now()

    @JsonProperty("cd_py_name")
    val cdPyName: String = ""

    @JsonProperty("cd_val")
    val cdVal: Int = 0

    @JsonProperty("cs_tp")
    val cdTp: Int = 0

    @ManyToOne
    @JoinColumn(name = "cd_no")
    val card: Card = card
}
