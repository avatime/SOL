package com.finance.backend.card

import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/card")
class CardController(
        val cardService: CardService
) {

    @PutMapping("/asset")
    fun registerCard(@RequestBody cdNoList: List<String>): ResponseEntity<Any>{
        return ResponseEntity
                .ok()
                .body(cardService.registerMain(cdNoList))
    }

    @GetMapping("/asset")
    fun getCardInfo(@RequestHeader("access_token") accessToken : String, @RequestBody acNo: String): ResponseEntity<Any>{
        return ResponseEntity
                .ok()
                .body(cardService.getAssetCard(accessToken))
    }

    @GetMapping("/bill/detail/{cd_no}/{year}/{month}")
    fun getCardMonthInfo(@PathVariable cdNo: String, @PathVariable year: Int, @PathVariable month:Int): ResponseEntity<Any>{
        return ResponseEntity
                .ok()
                .body(cardService.getCardMonthInfo(cdNo, year, month))
    }

    @GetMapping("/bill/{cd_no}/{year}/{month}")
    fun getCardMonthAll(@PathVariable cdNo: String, @PathVariable year: Int, @PathVariable month:Int): ResponseEntity<Any>{
        return ResponseEntity
                .ok()
                .body(cardService.getCardMonthAll(cdNo, year, month))
    }

    @GetMapping
    fun getCardBenefit(@RequestHeader("access_token") accessToken : String): ResponseEntity<Any>{
        return ResponseEntity
                .ok()
                .body(cardService.getCardBenefit(accessToken))
    }
}