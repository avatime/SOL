package com.finance.backend.notice

interface NoticeService {
    fun registNoticeToken(token : String, noticeRegistRequest: NoticeRegistRequest)
    fun sendAlarm(token: String?, msg : String)
}