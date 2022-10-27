package com.finance.backend.remit

import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.bank.Account
import com.finance.backend.bank.AccountRepository
import com.finance.backend.bank.response.RecentTradeRes
import com.finance.backend.bookmark.Bookmark
import com.finance.backend.bookmark.BookmarkRepository
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.corporation.CorporationRepository
import com.finance.backend.remit.request.RemitInfoReq
import com.finance.backend.tradeHistory.TradeHistory
import com.finance.backend.tradeHistory.TradeHistoryRepository
import com.finance.backend.user.User
import com.finance.backend.user.UserRepository
import org.springframework.stereotype.Service
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
        val userRepository: UserRepository
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
                        val account = accountRepository.findById(trade.tdTgAc).get()
                        val acName = account.acName
                        val cpLogo = corporationRepository.findById(account.acCpCode).get().cpLogo
                        accountDetailList.add(RecentTradeRes(acName, account.acNo, false, cpLogo))
                        checkList.add(trade.tdTgAc)
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
        val tradeRemitHistory = TradeHistory(value, date, 2, remitTarget, targetAccount, receive, send, remitAccount)
        tradeHistoryRepository.save(tradeRemitHistory)

        // 입금 거래 내역
        val depositAccount = accountRepository.findById(remitInfoReq.acReceive).get()
        val depositRemitHistory = TradeHistory(value, date, 1, remitInfoReq.acName, remitInfoReq.acSend, send, receive, depositAccount)
        tradeHistoryRepository.save(depositRemitHistory)
    }

    override fun putBookmark(acNo: String, token: String) {
        val user: User

        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()}) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            user = userRepository.findById(userId).get()
            if (bookmarkRepository.existsByUserIdAndAcNo(userId, acNo)){
                var bookmark = bookmarkRepository.findByUserIdAndAcNo(userId, acNo)
                bookmark.apply {
                    bkStatus = !bkStatus!!
                }
                bookmarkRepository.save(bookmark)
            }else{
                var bookmark = Bookmark(acNo, user, true)
                bookmarkRepository.save(bookmark)
            }
        }

    }
}