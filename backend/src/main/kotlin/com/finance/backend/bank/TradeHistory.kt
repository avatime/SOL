package com.finance.backend.bank

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "trade_history")
class TradeHistory(
    id : Long,
    value : Int,
    date : Date,
    content : String,
    type : Int,
    target : String,
    targetAccount : String,
    receive : String,
    send : String,
    account : Account

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = id

    @JsonProperty("td_val")
    var tdVal: Int = value

    @JsonProperty("td_dt")
    var tdDt: Date = date

    @JsonProperty("td_cn")
    var tdCn: String = content

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