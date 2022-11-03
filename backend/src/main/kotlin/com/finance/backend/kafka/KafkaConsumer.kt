package com.finance.backend.kafka

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumer {

    @KafkaListener(topics = ["remit"], groupId = "shinhan")
    fun consume(message: String?) {
        println(String.format("Consumed message : %s", message))
    }
}
