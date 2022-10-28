package com.finance.backend.finance

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/finance")
class FinanceController(
        val financeService: FinanceService
) {

    @GetMapping("/asset")
    fun getFinanceAsset(@RequestHeader("access_token") accessToken : String): ResponseEntity<Any> {
        return ResponseEntity
                .ok()
                .body(financeService.getFinanceAsset(accessToken))
    }

}