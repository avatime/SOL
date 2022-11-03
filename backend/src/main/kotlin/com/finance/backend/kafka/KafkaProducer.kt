package com.finance.backend.kafka

import com.finance.backend.remit.request.RemitInfoReq
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducer(val kafkaTemplate : KafkaTemplate<String, RemitInfoReq>) {

    private val TOPIC = "remit"

    fun sendMessage(message: RemitInfoReq?) {
        kafkaTemplate.send(TOPIC, message)
    }
}