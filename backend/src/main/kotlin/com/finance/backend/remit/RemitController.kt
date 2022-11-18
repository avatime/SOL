package com.finance.backend.remit

import com.finance.backend.kafka.KafkaProducer
import com.finance.backend.remit.request.RemitInfoReq
import com.finance.backend.remit.request.RemitNonMemberReq
import com.finance.backend.remit.request.RemitPhoneReq
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/remit")
class RemitController(val remitService: RemitService, val kafka: KafkaProducer) {

    @GetMapping("/recommendation")
    fun getRecommendationAccount(@RequestHeader("access_token") accessToken: String): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(remitService.getRecommendationAccount(accessToken))
    }

    @PostMapping("/account")
    fun postRemit(@RequestBody remitInfoReq: RemitInfoReq): ResponseEntity<Any>{
        kafka.accountMessage(remitInfoReq)
        return ResponseEntity.status(200).body(remitService.postRemit(remitInfoReq))
    }

    @PostMapping("/phone")
    fun postRemitPhone(@RequestBody remitPhoneReq: RemitPhoneReq): ResponseEntity<Any>{
        kafka.phoneMessage(remitPhoneReq)
        return ResponseEntity.status(200).body(remitService.postRemitPhone(remitPhoneReq))

    }

    @PutMapping("/bookmark")
    fun putBookmark(@RequestBody acNo: String, @RequestHeader("access_token") accessToken: String): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(remitService.putBookmark(acNo, accessToken))
    }

    @PostMapping("/phone/nonmember")
    fun postRemitNonMember(@RequestBody remitNonMemberReq: RemitNonMemberReq): ResponseEntity<Any>{
        println("postRemitNomMember")
        kafka.noMemberMessage(remitNonMemberReq)
        return try{
            ResponseEntity.status(200).body(remitService.postRemitPhoneNonMember(remitNonMemberReq))
        } catch (e :Exception) {
            ResponseEntity.status(500).body(e)
        }
    }

    @GetMapping("/phone/nonmember/{tokenId}")
    fun getRemitNonMember(@PathVariable tokenId : Long): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(remitService.getRemitPhoneNonMember(tokenId))
    }
}