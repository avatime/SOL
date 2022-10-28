package com.finance.backend.insurance

import com.finance.backend.insurance.response.InsuranceProductInfoDetailRes
import com.finance.backend.insurance.response.InsuranceProductInfoRes
import com.finance.backend.insurance.response.MyInsuranceInfoDetailRes
import com.finance.backend.insurance.response.MyInsuranceInfoRes

interface InsuranceService {
    fun getAllInsuranceProduct(accessToken : String) : List<InsuranceProductInfoRes>
    fun getInsuranceProductDetail(accessToken : String, isId : Long) : InsuranceProductInfoDetailRes
    fun getAllMyInsurance(accessToken: String) : MyInsuranceInfoRes
    fun getMyInsuranceDetail(accessToken: String, isId : Long) : MyInsuranceInfoDetailRes
    fun registMainOrNot(accessToken: String, isId : Long)
}