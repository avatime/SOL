package com.finance.backend.bank;

import com.finance.backend.bank.request.AccountInfoReq
import com.finance.backend.corporation.response.BankInfoRes
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
@RequestMapping("/api/v1/bank")
class AccountController(val accountService: AccountService) {
    @GetMapping("/asset/all")
    fun getAccountAll(@RequestHeader("access_token") accessToken : String): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(accountService.getAccount(accessToken))
    }

    @GetMapping("/asset")
    fun getAccount(@RequestHeader("access_token") accessToken : String): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(accountService.getAccountAll(accessToken))
    }

    @PutMapping("/asset")
    fun registerAccount(@RequestBody acNoList: List<String>): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(accountService.registerAccount(acNoList))
    }

    @PutMapping("/remit")
    fun registerRemitAccount(@RequestBody accountInfoReq: AccountInfoReq): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(accountService.registerRemitAccount(accountInfoReq.acNo))
    }

    @PutMapping("/bookmark")
    fun registerBookmarkAccount(@RequestHeader("access_token") accessToken : String, @RequestBody accountInfoReq: AccountInfoReq): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(accountService.registerBookmarkAccount(accountInfoReq.acNo, accessToken))
    }

    @GetMapping("/all/{acNo}")
    fun getAccountDetail(@PathVariable acNo : String): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(accountService.getAccountDetail(acNo))
    }

    @GetMapping("/{acNo}/{type}")
    fun getAccountDetailType(@PathVariable acNo: String, @PathVariable type: Int): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(accountService.getAccountDetailType(acNo, type))
    }

    @GetMapping("/recent")
    fun getRecentTrade(@RequestHeader("access_token") accessToken : String): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(accountService.getRecentTrade(accessToken))
    }

    @GetMapping("/check/{acNo}/{cpCode}")
    fun getCheckAccount(@PathVariable acNo: String, @PathVariable cpCode: Long): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(accountService.getUserName(acNo, cpCode))
    }

    @GetMapping("/info")
    fun getBankInfo(): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(accountService.getBankInfo())
    }

    @GetMapping("/finance/info")
    fun getFinanceInfo(): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(accountService.getFinanceInfo())
    }

    @GetMapping("/register")
    fun getBankRegister(@RequestHeader("access_token") accessToken : String):ResponseEntity<Any>{
        val response = accountService.getAccountRegistered(accessToken)
        println(response.accountList)
        println(response.financeList)
        println(response.insuranceList)
        return ResponseEntity.status(200).body(response)
    }
}
