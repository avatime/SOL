package com.finance.backend.finance

import com.finance.backend.corporation.CorporationRepository
import com.finance.backend.corporation.response.BankInfoRes
import org.springframework.stereotype.Service

@Service("FinanceService")
class FinanceServiceImpl(
        val corporationRepository: CorporationRepository
) : FinanceService {

    override fun getFinanceInfo(): List<BankInfoRes> {
        var bankInfoList = ArrayList<BankInfoRes>()
        val corporationList = corporationRepository.findTop25ByOrderByCpCodeDesc()
        for(corporation in corporationList){
            val bankInfo = BankInfoRes(corporation.cpName, corporation.cpLogo)
            bankInfoList.add(bankInfo)
        }
        return bankInfoList

    }
}