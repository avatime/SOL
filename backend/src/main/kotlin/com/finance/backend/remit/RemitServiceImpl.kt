package com.finance.backend.remit

import com.finance.backend.Exceptions.InvalidUserException
import com.finance.backend.Exceptions.NoAccountException
import com.finance.backend.Exceptions.RemitFailedException
import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.accountProduct.AccountProductRepository
import com.finance.backend.bank.Account
import com.finance.backend.bank.AccountRepository
import com.finance.backend.bank.response.RecentTradeRes
import com.finance.backend.bookmark.Bookmark
import com.finance.backend.bookmark.BookmarkRepository
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.corporation.CorporationRepository
import com.finance.backend.remit.request.RemitInfoReq
import com.finance.backend.remit.request.RemitPhoneReq
import com.finance.backend.tradeHistory.TradeHistory
import com.finance.backend.tradeHistory.TradeHistoryRepository
import com.finance.backend.user.User
import com.finance.backend.user.UserRepository
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

@Service("RemitService")
class RemitServiceImpl(
        val jwtUtils: JwtUtils,
        val bookmarkRepository: BookmarkRepository,
        val accountRepository: AccountRepository,
        val corporationRepository: CorporationRepository,
        val tradeHistoryRepository: TradeHistoryRepository,
        val userRepository: UserRepository,
        val accountProductRepository: AccountProductRepository
) : RemitService {

    override fun getRecommendationAccount(token: String): List<RecentTradeRes> {
        val accountDetailList = ArrayList<RecentTradeRes>()

        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()
                }){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            println(userRepository.findById(userId).get().name)
            // 사용자의 계좌 정보
            val accountList = accountRepository.findByUserId(userId)

            // 나의 계좌는 반환하지 않도록 체크
            val myAccountList = ArrayList<String>()
            for (accountInfo in accountList){
                myAccountList.add(accountInfo.acNo)
            }

            // 북마크 계좌 모음
            val checkBookmarkList = ArrayList<String>()

            // 내가 북마크 한 계좌 하나씩 뽑기
            val bookmarkTradeHistoryList = tradeHistoryRepository.getBookMarkTradeHistoriesByUserId(userId).orEmpty()
            for (bookmark in bookmarkTradeHistoryList){
                if (!myAccountList.contains(bookmark.tdTgAc)){
                    val bookmarkAccount = accountRepository.findById(bookmark.tdTgAc!!).get()
                    val bookmarkUser = userRepository.findById(bookmarkAccount.user.id).get()
                    val bookmarkCorporation = corporationRepository.findById(bookmarkAccount.acCpCode).get()
                    accountDetailList.add(RecentTradeRes(bookmarkUser.name, bookmark.tdTgAc!!, bookmarkCorporation.cpName, true, bookmarkCorporation.cpLogo, bookmark.tdDt))
                    checkBookmarkList.add(bookmark.tdTgAc!!)
                }
            }

            // 최근 거래 계좌 추가
            val recentAccountList = tradeHistoryRepository.getTradeHistoriesByUserId(userId).orEmpty()

            for (recentHistory in recentAccountList){
                if (!myAccountList.contains(recentHistory.tdTgAc) && !checkBookmarkList.contains(recentHistory.tdTgAc)){
                    val recentAccount = accountRepository.findById(recentHistory.tdTgAc!!).get()
                    val recentUser = userRepository.findById(recentAccount.user.id).get()
                    val recentCorporation = corporationRepository.findById(recentAccount.acCpCode).get()
                    accountDetailList.add(RecentTradeRes(recentUser.name, recentAccount.acNo, recentCorporation.cpName, false, recentCorporation.cpLogo, recentHistory.tdDt))
                }
            }
        }
        return accountDetailList
    }

    override fun postRemit(remitInfoReq: RemitInfoReq) {
        val value = remitInfoReq.value
        val date = LocalDateTime.now()
        val remitTarget = remitInfoReq.acTag
        val targetAccount = remitInfoReq.acReceive
        val receive = remitInfoReq.receive
        val send = remitInfoReq.send
        val remitAccount = accountRepository.findById(remitInfoReq.acSend).get()

        // 출금 거래 내역
        val tradeRemitHistory = TradeHistory("출금",value, date, 2, remitTarget, targetAccount, receive, send, remitAccount)
        tradeHistoryRepository.save(tradeRemitHistory)
        val accountRemit = accountRepository.findById(remitInfoReq.acSend).get()
        if (accountRemit.balance >= value){
            accountRemit.withdraw(value)
            accountRepository.save(accountRemit)
        }else{
            throw RemitFailedException()
        }


        // 입금 거래 내역
        val depositAccount = accountRepository.findById(remitInfoReq.acReceive).get()
        val depositRemitHistory = TradeHistory("입금",value, date, 1, remitInfoReq.acName, remitInfoReq.acSend, send, receive, depositAccount)
        tradeHistoryRepository.save(depositRemitHistory)
        val accountDeposit = accountRepository.findById(remitInfoReq.acReceive).get()
        accountDeposit.deposit(value)
        accountRepository.save(accountDeposit)

    }

    override fun postRemitPhone(remitPhoneReq: RemitPhoneReq) {
        val phone = remitPhoneReq.phone
        val value = remitPhoneReq.value
        val date = LocalDateTime.now()

        if (userRepository.existsByPhone(phone)){

            val remitTarget = remitPhoneReq.acTag
            val targetAccount = remitPhoneReq.acReceive
            val receive = remitPhoneReq.receive
            val send = remitPhoneReq.send
            val remitAccount = accountRepository.findById(remitPhoneReq.acSend).get()

            // 출금 거래 내역
            val tradeRemitHistory = TradeHistory("",value, date, 2, remitTarget, targetAccount, receive, send, remitAccount)
            tradeHistoryRepository.save(tradeRemitHistory)
            val accountRemit = accountRepository.findById(remitPhoneReq.acSend).get()
            if (accountRemit.balance >= value){
                accountRemit.withdraw(value)
                accountRepository.save(accountRemit)
            }else{
                throw RemitFailedException()
            }

            // 입금 거래 내역
            val depositAccount = accountRepository.findById(remitPhoneReq.acReceive).get()
            val depositRemitHistory = TradeHistory("",value, date, 1, remitPhoneReq.acName, remitPhoneReq.acSend, send, receive, depositAccount)
            tradeHistoryRepository.save(depositRemitHistory)
            val accountDeposit = accountRepository.findById(remitPhoneReq.acReceive).get()
            accountDeposit.deposit(value)
            accountRepository.save(accountDeposit)

        }else{
            // 비회원 user와 account 만들기
            val acNo = remitPhoneReq.acReceive
            val balance: Long = 100000000
            val user = User("", "", phone, Timestamp.valueOf(LocalDateTime.now()), 2, "비회원")
            userRepository.save(user)

            val acType = 1
            val acName = remitPhoneReq.acTag
            val corporation = corporationRepository.findByCpName(acName)!!
            val acCpCode = corporation.cpCode
            val accountProduct = accountProductRepository.findByCpCode(acCpCode)
            val acPdCode = accountProduct.acPdCode
            val acStatus = 10
            val acDate = LocalDateTime.now()

            val account = Account(acNo, balance, user, acType, acName, acPdCode, acCpCode, acStatus, acDate)
            accountRepository.save(account)

            val receive = remitPhoneReq.receive
            val send = remitPhoneReq.send
            val remitAccount = accountRepository.findById(remitPhoneReq.acSend).get()

            // 출금 거래 내역
            val tradeRemitHistory = TradeHistory("", value, date, 2, acName, acNo, receive, send, remitAccount)
            tradeHistoryRepository.save(tradeRemitHistory)
            val accountRemit = accountRepository.findById(remitPhoneReq.acSend).get()
            if (accountRemit.balance >= value){
                accountRemit.withdraw(value)
                accountRepository.save(accountRemit)
            }else{
                throw RemitFailedException()
            }

            // 입금 거래 내역
            val depositAccount = accountRepository.findById(remitPhoneReq.acReceive).get()
            val depositRemitHistory = TradeHistory("",value, date, 1, remitPhoneReq.acName, remitPhoneReq.acSend, send, receive, depositAccount)
            tradeHistoryRepository.save(depositRemitHistory)
            val accountDeposit = accountRepository.findById(remitPhoneReq.acReceive).get()
            accountDeposit.deposit(value)
            accountRepository.save(accountDeposit)
        }

    }

    override fun putBookmark(acNo: String, token: String) {
        val user: User

        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()}) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            user = userRepository.findById(userId).get()
            if (bookmarkRepository.existsByUserIdAndAcNo(userId, acNo)){
                var bookmark = bookmarkRepository.findByUserIdAndAcNo(userId, acNo)
                bookmark.apply {
                    bkStatus = !bkStatus
                }
                bookmarkRepository.save(bookmark)
            }else{
                var bookmark = Bookmark(acNo, user, true)
                bookmarkRepository.save(bookmark)
            }
        }

    }
}