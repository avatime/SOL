package com.finance.backend.remit

import com.finance.backend.remit.request.RemitInfoReq
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/vi/remit")
class RemitController(val remitService: RemitService) {
    @GetMapping("/recommendation")
    fun getRecommendationAccount(@RequestHeader("access_token") accessToken: String): ResponseEntity<Any>{
        return ResponseEntity
                .ok()
                .body(remitService.getRecommendationAccount(accessToken))
    }

    @PostMapping
    fun postRemit(@RequestBody remitInfoReq: RemitInfoReq): ResponseEntity<Any>{
        return ResponseEntity
                .ok()
                .body(remitService.postRemit(remitInfoReq))
    }
}