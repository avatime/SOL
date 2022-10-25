package com.finance.backend.bank;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable
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

    @PutMapping("/remit")
    fun registerRemitAccount(@RequestBody acNo: String): ResponseEntity<Any>{
        return ResponseEntity
                .ok()
                .body(accountService.registerRemitAccount(acNo))
    }

    @PutMapping("/bookmark")
    fun registerBookmarkAccount(@RequestHeader("acces_token") accessToken : String, @RequestBody acNo: String): ResponseEntity<Any>{
        return ResponseEntity
                .ok()
                .body(accountService.registerBookmarkAccount(acNo,accessToken))
    }

    @GetMapping("/all/{ac_no}")
    fun getAccountDetail(@PathVariable acNo : String): ResponseEntity<Any>{
        return ResponseEntity
                .ok()
                .body(accountService.getAccountDetail(acNo))
    }

    @GetMapping("/{ac_no}/{type}")
    fun getAccountDetailType(@PathVariable acNo: String, @PathVariable type: Int): ResponseEntity<Any>{
        return ResponseEntity
                .ok()
                .body(accountService.getAccountDetailType(acNo, type))
    }

    @GetMapping("/recent")
    fun getRecentTrade(@RequestHeader("acces_token") accessToken : String): ResponseEntity<Any>{
        return ResponseEntity
                .ok()
                .body(accountService.getRecentTrade(accessToken))
    }

    @GetMapping("/check/{ac_no}/{cp_code}")
    fun getCheckAccount(@PathVariable acNo: String, @PathVariable cpCode: Long): ResponseEntity<Any>{
        return ResponseEntity
                .ok()
                .body(accountService.getUserName(acNo, cpCode))
    }
}
