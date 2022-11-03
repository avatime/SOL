package com.finance.backend.remit

import com.finance.backend.kafka.KafkaProducer
import com.finance.backend.remit.request.RemitInfoReq
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/remit")
class RemitController(val remitService: RemitService, val kafka: KafkaProducer) {

    @GetMapping("/recommendation")
    fun getRecommendationAccount(@RequestHeader("access_token") accessToken: String): ResponseEntity<Any>{
        return ResponseEntity
                .ok()
                .body(remitService.getRecommendationAccount(accessToken))
    }

    @PostMapping("/account")
    fun postRemit(@RequestBody remitInfoReq: RemitInfoReq): ResponseEntity<Any>{
        kafka.sendMessage(remitInfoReq)
        return ResponseEntity
                .ok()
                .body(remitService.postRemit(remitInfoReq))
    }

    @PutMapping("/bookmark")
    fun putBookmark(@RequestBody acNo: String, @RequestHeader("access_token") accessToken: String): ResponseEntity<Any>{
        return ResponseEntity
                .ok()
                .body(remitService.putBookmark(acNo, accessToken))
    }
}