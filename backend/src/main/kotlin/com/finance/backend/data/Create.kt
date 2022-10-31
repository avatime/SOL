package com.finance.backend.data

import com.finance.backend.bank.Account
import com.finance.backend.bank.AccountRepository
import com.finance.backend.data.request.AccountReq
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
        val userRepository: UserRepository
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
}