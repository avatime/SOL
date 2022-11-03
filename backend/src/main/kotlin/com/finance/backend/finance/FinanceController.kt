package com.finance.backend.finance

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/finance")
class FinanceController(
        val financeService: FinanceService
) {

    @GetMapping("/asset/all")
    fun getFinanceAssetAll(@RequestHeader("access_token") accessToken : String): ResponseEntity<Any> {
        return ResponseEntity.status(200).body(financeService.getFinanceAssetAll(accessToken))
    }

    @GetMapping("/asset")
    fun getFinanceAsset(@RequestHeader("access_token") accessToken : String): ResponseEntity<Any> {
        return ResponseEntity.status(200).body(financeService.getFinanceAsset(accessToken))
    }

    @PutMapping("/asset")
    fun putFinanceAsset(@RequestBody acNoList: List<String>): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(financeService.putFinanceAsset(acNoList))
    }

}