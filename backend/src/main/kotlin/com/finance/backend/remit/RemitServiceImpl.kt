package com.finance.backend.remit

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
        var accountDetailList = ArrayList<RecentTradeRes>()

        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()
                }){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))

            // 북마크 계좌 추가
            val bookmarkAccountList : List<Bookmark> = bookmarkRepository.findByUserId(userId)
            var checkList = ArrayList<String>()
            for (bookmarkAccount in bookmarkAccountList){
                var account : Account = accountRepository.findById(bookmarkAccount.acNo).get()
                var acName : String = account.acName
                var cpLogo = corporationRepository.findById(account.acCpCode).get().cpLogo
                accountDetailList.add(RecentTradeRes(acName, bookmarkAccount.acNo, bookmarkAccount.bkStatus, cpLogo))
                checkList.add(bookmarkAccount.acNo)
            }

            // 최근 거래 계좌 추가
            val accountList = accountRepository.findByUserId(userId)
            for (account in accountList){
                val tradeHistoryList = tradeHistoryRepository.getRecentTrade(account.acNo)
                for (trade in tradeHistoryList){
                    if(!checkList.contains(trade.tdTgAc)){
                        val account = accountRepository.findById(trade.tdTgAc!!).get()
                        val acName = account.acName
                        val cpLogo = corporationRepository.findById(account.acCpCode).get().cpLogo
                        accountDetailList.add(RecentTradeRes(acName, account.acNo, false, cpLogo))
                        checkList.add(trade.tdTgAc!!)
                    }
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
        val tradeRemitHistory = TradeHistory("",value, date, 2, remitTarget, targetAccount, receive, send, remitAccount)
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
        val depositRemitHistory = TradeHistory("",value, date, 1, remitInfoReq.acName, remitInfoReq.acSend, send, receive, depositAccount)
        tradeHistoryRepository.save(depositRemitHistory)
        val accountDeposit = accountRepository.findById(remitInfoReq.acReceive).get()
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
            accountRepository.save(accountDeposit)

        }else{
            // 비회원 user와 account 만들기
            val acNo = remitPhoneReq.acReceive
            val balance = 100000000
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