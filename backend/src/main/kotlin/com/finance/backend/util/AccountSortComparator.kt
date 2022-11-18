package com.finance.backend.util

import com.finance.backend.bank.response.BankAccountRes

class AccountSortComparator : Comparator<BankAccountRes>{
    override fun compare(o1: BankAccountRes, o2: BankAccountRes): Int {
        if (o1.acMain > o2.acMain){
            return 1
        } else if ( o1.acMain < o2.acMain){
            return -1
        }
        return 0
    }
}