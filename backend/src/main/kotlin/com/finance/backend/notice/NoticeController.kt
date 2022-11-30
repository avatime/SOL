package com.finance.backend.notice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/notice")
class NoticeController(private val noticeService: NoticeService) {

    @PutMapping("")
    fun registToken(@RequestHeader("access_token") accessToken : String, @RequestBody noticeRegistRequest: NoticeRegistRequest) : ResponseEntity<Any?> {
        return ResponseEntity.status(200).body(noticeService.registNoticeToken(accessToken, noticeRegistRequest))
    }
}