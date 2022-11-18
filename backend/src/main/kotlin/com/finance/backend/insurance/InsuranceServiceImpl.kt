package com.finance.backend.insurance

import com.finance.backend.Exceptions.*
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.insurance.entity.Insurance
import com.finance.backend.insurance.entity.IsDetail
import com.finance.backend.insurance.entity.IsProduct
import com.finance.backend.insurance.repository.InsuranceRepository
import com.finance.backend.insurance.repository.IsDetailRepository
import com.finance.backend.insurance.repository.IsProductRepository
import com.finance.backend.insurance.request.InsuranceReq
import com.finance.backend.insurance.response.InsuranceProductInfoDetailRes
import com.finance.backend.insurance.response.InsuranceProductInfoRes
import com.finance.backend.insurance.response.MyInsuranceInfoDetailRes
import com.finance.backend.insurance.response.MyInsuranceInfoRes
import com.finance.backend.user.User
import com.finance.backend.user.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service("InsuranceService")
@RequiredArgsConstructor
class InsuranceServiceImpl (
        private val insuranceRepository: InsuranceRepository,
        private val isDetailRepository: IsDetailRepository,
        private val isProductRepository: IsProductRepository,
        private val userRepository: UserRepository,
        private val jwtUtils: JwtUtils
        ): InsuranceService {
    override fun getAllInsuranceProduct(accessToken: String): List<InsuranceProductInfoRes> {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()}){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElse(null) ?: throw InvalidUserException()
            val list : List<IsProduct> = isProductRepository.findAll()
            return List(list.size) {i -> list[i].toEntity()}
        } else throw Exception()
    }

    override fun getInsuranceProductDetail(accessToken: String, isId : Long): InsuranceProductInfoDetailRes {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()}){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElse(null) ?: throw InvalidUserException()
            val insuranceProduct : IsProduct = isProductRepository.findById(isId).orElse(null)?: throw NoSuchElementException()
            val list : List<IsDetail> = isDetailRepository.findAllByIsPd(insuranceProduct)
            return InsuranceProductInfoDetailRes(isId, insuranceProduct.isPdName, insuranceProduct.isPdType, List(list.size) {i -> list[i].isPdDetail})
        } else throw Exception()
    }

    override fun getAllMyRegistInsurance(accessToken: String): MyInsuranceInfoRes {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()}){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElse(null) ?: throw InvalidUserException()
            val insuranceList : List<Insurance> = insuranceRepository.findAllByUserIdAndIsRegAndIsStatus(user.id, true,10)
            val list = List(insuranceList.size) {i -> insuranceList[i].toEntity(isProductRepository.findById(insuranceList[i].isPdCode).orElse(null)?.isPdName ?: throw NoSuchElementException())}
            var fee = 0
            for (insurance in insuranceList) fee += insurance.fee
            return MyInsuranceInfoRes(fee, list)
        } else throw Exception()
    }

    override fun getAllMyInsurance(accessToken: String): List<MyInsuranceInfoDetailRes> {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()}){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElse(null) ?: throw InvalidUserException()
            val insuranceList : List<Insurance> = insuranceRepository.findAllByUserAndIsStatus(user, 10)
            return List(insuranceList.size) {i -> insuranceList[i].toEntity(isProductRepository.findById(insuranceList[i].isPdCode).orElse(null)?.isPdName ?: throw NoSuchElementException())}
        } else throw Exception()
    }

    override fun getMyInsuranceDetail(accessToken: String, isId : Long): MyInsuranceInfoDetailRes {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()}){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElse(null) ?: throw InvalidUserException()
            val insurance : Insurance = insuranceRepository.findByIdAndUserAndIsStatus(isId, user, 10)?: throw NoSuchElementException()
            val insuranceProduct : IsProduct = isProductRepository.findById(insurance.isPdCode).orElse(null) ?: throw NoSuchElementException()
            return insurance.toEntity(insuranceProduct.isPdName)
        } else throw Exception()
    }

    override fun registApplication(accessToken: String, registList : List<InsuranceReq>) {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException()}){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElse(null) ?: throw InvalidUserException()
            for(insId in registList){
                val insurance: Insurance = insuranceRepository.findByIdAndUserAndIsStatus(insId.isId, user, 10)
                        ?: throw NoSuchElementException()
                insurance.registApp()
                insuranceRepository.save(insurance)
            }
        }
    }
}