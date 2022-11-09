package com.finance.backend.card

import com.finance.backend.card.request.CardInfoReq
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.StringJoiner

@RestController
@RequestMapping("/api/v1/card")
class CardController(
        val cardService: CardService
) {

    @PutMapping("/asset")
    fun registerCard(@RequestBody cdNoList: List<CardInfoReq>): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(cardService.registerMain(cdNoList))
    }

    @GetMapping("/asset")
    fun getCardInfo(@RequestHeader("access_token") accessToken : String): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(cardService.getAssetCard(accessToken))
    }

    @GetMapping("/asset/my")
    fun getMyCardInfo(@RequestHeader("access_token") accessToken : String): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(cardService.getMyCard(accessToken))
    }

    @GetMapping("/bill/detail/{cdNo}/{year}/{month}")
    fun getCardMonthInfo(@PathVariable cdNo: String, @PathVariable year: Int, @PathVariable month:Int): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(cardService.getCardMonthInfo(cdNo, year, month))
    }

    @GetMapping("/bill/{cdNo}/{year}/{month}")
    fun getCardMonthAll(@PathVariable cdNo: String, @PathVariable year: Int, @PathVariable month:Int): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(cardService.getCardMonthAll(cdNo, year, month))
    }

//    @GetMapping
//    fun getCardBenefit(@RequestHeader("access_token") accessToken : String): ResponseEntity<Any>{
//        return ResponseEntity.status(200).body(cardService.getCardBenefit(accessToken))
//    }

    @GetMapping("/{cdPdCode}")
    fun getCardBenefitDetail(@PathVariable cdPdCode: Long): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(cardService.getCardBenefitDetail(cdPdCode))
    }

    @GetMapping("/benefit/{cdPdCode}")
    fun getCardBenefitDetail3(@PathVariable cdPdCode: Long): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(cardService.getCardBenefitThree(cdPdCode))
    }
}