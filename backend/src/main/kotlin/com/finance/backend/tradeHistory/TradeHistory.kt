package com.finance.backend.tradeHistory

import com.fasterxml.jackson.annotation.JsonProperty
import com.finance.backend.bank.Account
import com.finance.backend.group.response.PublicTradeRes
import java.time.LocalDateTime
import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "trade_history")
class TradeHistory(
    tdCn : String,
    value : Long,
    date : LocalDateTime,
    type : Int,
    target : String,
    targetAccount : String?,
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
    var tdVal: Long = value

    @JsonProperty("td_dt")
    var tdDt: LocalDateTime = date

    @JsonProperty("td_cn")
    var tdCn: String = tdCn

    @JsonProperty("td_type")
    var tdType: Int = type

    @JsonProperty("td_tg")
    var tdTg: String = target

    @JsonProperty("td_tg_ac")
    var tdTgAc: String? = targetAccount

    @JsonProperty("td_rec")
    var tdRec: String = receive

    @JsonProperty("td_sed")
    var tdSed: String = send

    @ManyToOne
    @JoinColumn(name = "ac_no")
    val account: Account = account

    fun toEntity(userType: String) : PublicTradeRes = PublicTradeRes(this.tdDt, this.tdVal, this.tdTg, this.tdSed, if(this.tdType == 1) "출금" else "입금", userType)
}