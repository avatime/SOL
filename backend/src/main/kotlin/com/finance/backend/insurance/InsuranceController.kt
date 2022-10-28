package com.finance.backend.insurance

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/insurance")
class InsuranceController {

    @GetMapping("")
    fun getAllInsuranceProduct(@RequestHeader("access_token") accessToken : String) {

    }

    @GetMapping("/{id}")
    fun getInsuranceProduct(@RequestHeader("access_token") accessToken : String, @PathVariable id: String) {

    }
}