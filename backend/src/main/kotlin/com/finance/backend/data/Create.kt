package com.finance.backend.data

import com.finance.backend.bank.Account
import com.finance.backend.bank.AccountRepository
import com.finance.backend.data.request.AccountReq
import com.finance.backend.data.request.TradeReq
import com.finance.backend.tradeHistory.TradeHistory
import com.finance.backend.tradeHistory.TradeHistoryRepository
import com.finance.backend.user.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.*
import kotlin.reflect.typeOf

@RestController
@RequestMapping("/api/v1/data")
class Create(
        val accountRepository: AccountRepository,
        val userRepository: UserRepository,
        val tradeHistoryRepository: TradeHistoryRepository
) {

    @PostMapping("/account")
    fun postAccount(@RequestBody accountReq: AccountReq): ResponseEntity<Any>{
        val userId = accountReq.user
        val user = userRepository.findById(UUID.fromString(userId)).get()
        println("user: " + user)
        val account = Account(accountReq.acNo, accountReq.balance, user, accountReq.acType, accountReq.acName, accountReq.acPdCode, accountReq.acCpCode, accountReq.acStatus, LocalDateTime.now())
        return ResponseEntity
                .ok()
                .body(accountRepository.save(account))
    }

    @PostMapping("/trade")
    fun postTrade(@RequestBody tradeReq: TradeReq): ResponseEntity<Any>{
        val accountSend = accountRepository.findById(tradeReq.acNo).get()
        val tradeSend = TradeHistory(tradeReq.tdVal, LocalDateTime.now(), 2, tradeReq.tdTg, tradeReq.tdTgAc, tradeReq.tdRec, tradeReq.tdSed, accountSend)

        val accountReceived = accountRepository.findById(tradeReq.tdTgAc).get()
        val tradeReceived = TradeHistory(tradeReq.tdVal, LocalDateTime.now(), 1, accountSend.acName, tradeReq.acNo, tradeReq.tdSed, tradeReq.tdRec, accountReceived)

        tradeHistoryRepository.save(tradeSend)
        tradeHistoryRepository.save(tradeReceived)

        return ResponseEntity
                .ok()
                .body("거래 완료")
    }
}