package com.finance.backend.remit

import com.finance.backend.remit.request.RemitInfoReq
import com.finance.backend.remit.request.RemitPhoneReq
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
class RemitController(val remitService: RemitService) {
    @GetMapping("/recommendation")
    fun getRecommendationAccount(@RequestHeader("access_token") accessToken: String): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(remitService.getRecommendationAccount(accessToken))
    }

    @PostMapping("/account")
    fun postRemit(@RequestBody remitInfoReq: RemitInfoReq): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(remitService.postRemit(remitInfoReq))
    }

    @PostMapping("/phone")
    fun postRemitPhone(@RequestBody remitPhoneReq: RemitPhoneReq): ResponseEntity<Any>{
        return ResponseEntity
                .ok()
                .body(remitService.postRemitPhone(remitPhoneReq))
    }

    @PutMapping("/bookmark")
    fun putBookmark(@RequestBody acNo: String, @RequestHeader("access_token") accessToken: String): ResponseEntity<Any>{
        return ResponseEntity
                .ok()
                .body(remitService.putBookmark(acNo, accessToken))
    }
}