package com.finance.backend.insurance

import com.finance.backend.insurance.request.InsuranceReq
import com.finance.backend.insurance.response.InsuranceProductInfoDetailRes
import com.finance.backend.insurance.response.InsuranceProductInfoRes
import com.finance.backend.insurance.response.MyInsuranceInfoDetailRes
import com.finance.backend.insurance.response.MyInsuranceInfoRes

interface InsuranceService {
    fun getAllInsuranceProduct(accessToken : String) : List<InsuranceProductInfoRes>
    fun getInsuranceProductDetail(accessToken : String, isId : Long) : InsuranceProductInfoDetailRes
    fun getAllMyRegistInsurance(accessToken: String) : MyInsuranceInfoRes
    fun getAllMyInsurance(accessToken: String) : List<MyInsuranceInfoDetailRes>
    fun getMyInsuranceDetail(accessToken: String, isId : Long) : MyInsuranceInfoDetailRes
    fun registApplication(accessToken: String, registList : List<InsuranceReq>)
}