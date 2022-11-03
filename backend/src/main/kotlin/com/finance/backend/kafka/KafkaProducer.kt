package com.finance.backend.kafka

import com.finance.backend.remit.request.RemitInfoReq
import com.finance.backend.remit.request.RemitPhoneReq
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducer(val kafkaTemplate : KafkaTemplate<String, Any>) {

    private val TOPIC1 = "account"
    private val TOPIC2 = "phone"

    fun accountMessage(message: RemitInfoReq?) {
        kafkaTemplate.send(TOPIC1, message)
    }

    fun phoneMessage(message: RemitPhoneReq?) {
        kafkaTemplate.send(TOPIC2, message)
    }
}