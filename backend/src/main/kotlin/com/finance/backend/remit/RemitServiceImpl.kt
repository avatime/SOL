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
        val tdType = 2
        val target = remitInfoReq.acTag
        val targetAccount = remitInfoReq.acReceive
        val receive = remitInfoReq.receive
        val send = remitInfoReq.send
        val account = accountRepository.findById(remitInfoReq.acSend).get()

        val tradeHistory = TradeHistory(value, date, tdType, target, targetAccount, receive, send, account)

        tradeHistoryRepository.save(tradeHistory)
    }

    override fun putBookmark(acNo: String) {


    }
}