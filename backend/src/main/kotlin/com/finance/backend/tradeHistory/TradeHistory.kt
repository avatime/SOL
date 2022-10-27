package com.finance.backend.tradeHistory

import com.fasterxml.jackson.annotation.JsonProperty
import com.finance.backend.bank.Account
import java.time.LocalDateTime
import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "trade_history")
class TradeHistory(
    value : Int,
    date : LocalDateTime,
    type : Int,
    target : String,
    targetAccount : String,
    receive : String,
    send : String,
    account : Account

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    var id: Long = 0
        protected set

    @JsonProperty("td_val")
    var tdVal: Int = value

    @JsonProperty("td_dt")
    var tdDt: LocalDateTime = date

    @JsonProperty("td_cn")
    var tdCn: String = ""

    @JsonProperty("td_type")
    var tdType: Int = type

    @JsonProperty("td_tg")
    var tdTg: String = target

    @JsonProperty("td_tg_ac")
    var tdTgAc: String = targetAccount

    @JsonProperty("td_rec")
    var tdRec: String = receive

    @JsonProperty("td_sed")
    var tdSed: String = send

    @ManyToOne
    val account: Account = account
}