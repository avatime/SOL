package com.finance.backend.bank;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vi/bank")
class AccountController(val accountService: AccountService) {

    @GetMapping("/asset")
    fun getAccountAll(@RequestHeader("acces_token") accessToken : String): ResponseEntity<Any>{
        return ResponseEntity
                .ok()
                .body(accountService.getAccountAll(accessToken))
    }

    @PutMapping("/asset")
    fun registerAccount(@RequestBody acNo: String): ResponseEntity<Any>{
        return ResponseEntity
                .ok()
                .body(accountService.registerAccount(acNo))
    }
}
