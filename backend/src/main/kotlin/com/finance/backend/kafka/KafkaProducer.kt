package com.finance.backend.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestParam

@Service
class KafkaProducer(val kafkaTemplate : KafkaTemplate<String, String>) {

    private val TOPIC = "exam"

    fun sendMessage(@RequestParam("message") message: String?) {
        println(String.format("Produce message : %s", message))
        kafkaTemplate.send(TOPIC, message)
    }
}