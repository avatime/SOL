package com.finance.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import java.time.LocalDateTime
import java.util.*
import javax.annotation.PostConstruct

@EnableScheduling
@SpringBootApplication
class BackendApplication

@PostConstruct
fun started() {
	TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
	println("현재 시각 : " + LocalDateTime.now())
}

fun main(args: Array<String>) {
	runApplication<BackendApplication>(*args)
}
