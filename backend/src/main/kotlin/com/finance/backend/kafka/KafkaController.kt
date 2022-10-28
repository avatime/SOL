package com.finance.backend.kafka

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/v1/")
class KafkaController(val producer: KafkaProducer) {

    @PostMapping("kafka")
    fun sendMessage(@RequestParam("message") message: String?): String {
        producer.sendMessage(message)
        return "success"
    }
}