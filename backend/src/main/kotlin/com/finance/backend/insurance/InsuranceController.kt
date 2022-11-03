package com.finance.backend.insurance

import com.finance.backend.insurance.request.InsuranceReq
import com.finance.backend.insurance.response.MyInsuranceInfoRes
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/insurance")
class InsuranceController (private val insuranceService: InsuranceService) {

    @GetMapping("")
    fun getAllInsuranceProduct(@RequestHeader("access_token") accessToken : String) : ResponseEntity<Any> {
        return ResponseEntity.status(200).body(insuranceService.getAllInsuranceProduct(accessToken))
    }

    @GetMapping("/{id}")
    fun getInsuranceProductDetail(@RequestHeader("access_token") accessToken : String, @PathVariable id: Long) : ResponseEntity<Any> {
        return ResponseEntity.status(200).body(insuranceService.getInsuranceProductDetail(accessToken, id))
    }

    @GetMapping("/my")
    fun getAllMyRegistInsurance(@RequestHeader("access_token") accessToken : String) : ResponseEntity<Any> {
        return ResponseEntity.status(200).body(insuranceService.getAllMyRegistInsurance(accessToken))
    }

    @GetMapping("/my/all")
    fun getAllMyInsurance(@RequestHeader("access_token") accessToken : String) : ResponseEntity<Any> {
        return ResponseEntity.status(200).body(insuranceService.getAllMyInsurance(accessToken))
    }

    @PostMapping("/my")
    fun getMyInsuranceDetail(@RequestHeader("access_token") accessToken : String, @RequestBody insuranceReq: InsuranceReq) : ResponseEntity<Any>{
        return ResponseEntity.status(200).body(insuranceService.getMyInsuranceDetail(accessToken, insuranceReq.isId))
    }

    @PutMapping("/my")
    fun registMainOrNot(@RequestHeader("access_token") accessToken : String, @RequestBody registList : List<InsuranceReq> ): ResponseEntity<Any> {
        return ResponseEntity.status(200).body(insuranceService.registApplication(accessToken, registList))
    }
}