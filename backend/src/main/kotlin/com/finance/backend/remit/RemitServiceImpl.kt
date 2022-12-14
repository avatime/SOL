package com.finance.backend.remit

import com.finance.backend.Exceptions.*
import com.finance.backend.accountProduct.AccountProductRepository
import com.finance.backend.bank.Account
import com.finance.backend.bank.AccountRepository
import com.finance.backend.bank.response.RecentTradeRes
import com.finance.backend.bookmark.Bookmark
import com.finance.backend.bookmark.BookmarkRepository
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.corporation.CorporationRepository
import com.finance.backend.notice.NoticeService
import com.finance.backend.notice.NoticeServiceImpl
import com.finance.backend.remit.request.RemitInfoReq
import com.finance.backend.remit.request.RemitNonMemberReq
import com.finance.backend.remit.request.RemitPhoneReq
import com.finance.backend.remit.response.RemitTokenRes
import com.finance.backend.tradeHistory.TradeHistory
import com.finance.backend.tradeHistory.TradeHistoryRepository
import com.finance.backend.user.User
import com.finance.backend.user.UserRepository
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.text.DecimalFormat
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
        val remitAvailableRepository: RemitAvailableRepository,
        val userRepository: UserRepository,
        private val noticeService: NoticeService
) : RemitService {

    override fun getRecommendationAccount(token: String): List<RecentTradeRes> {
        val accountDetailList = ArrayList<RecentTradeRes>()

        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()
                }){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))

            // ???????????? ?????? ??????
            val accountList = accountRepository.findByUserId(userId).orEmpty()

            // ?????? ????????? ???????????? ????????? ??????
            val myAccountList = ArrayList<String>()
            for (accountInfo in accountList){
                myAccountList.add(accountInfo.acNo)
            }

            // ????????? ?????? ??????
            val checkBookmarkList = ArrayList<String>()

            // ?????? ????????? ??? ?????? ????????? ??????
            val bookmarkTradeHistoryList = tradeHistoryRepository.getBookMarkTradeHistoriesByUserId(userId).orEmpty()
            for (bookmark in bookmarkTradeHistoryList){
                if (!myAccountList.contains(bookmark.tdTgAc) && !checkBookmarkList.contains(bookmark.tdTgAc)){
                    val bookmarkAccount = accountRepository.findById(bookmark.tdTgAc!!).orElse(null)?: throw NoAccountException()
                    val bookmarkUser = userRepository.findById(bookmarkAccount.user.id).orElse(null)
                    val bookmarkCorporation = corporationRepository.findById(bookmarkAccount.acCpCode).orElse(null)
                    accountDetailList.add(RecentTradeRes(bookmarkUser.name, bookmark.tdTgAc!!, bookmarkCorporation.cpName, true, bookmarkCorporation.cpLogo, bookmark.tdDt))
                    checkBookmarkList.add(bookmark.tdTgAc!!)
                }
            }

            // ?????? ?????? ?????? ??????
            val list = tradeHistoryRepository.getTradeHistoriesByUserId(userId).orEmpty()
            val recentAccountList = list.reversed()
            for (recentHistory in recentAccountList){
                if (!myAccountList.contains(recentHistory.tdTgAc) && !checkBookmarkList.contains(recentHistory.tdTgAc)){
                    if(recentHistory.tdTgAc!!.contains("????????????")) continue
                    val recentAccount = accountRepository.findById(recentHistory.tdTgAc!!).orElse(null)?: if(recentHistory.tdCn != "?????????") throw NoAccountException() else continue
                    val recentUser = userRepository.findById(recentAccount.user.id).orElse(null)
                    val recentCorporation = corporationRepository.findById(recentAccount.acCpCode).orElse(null)
                    accountDetailList.add(RecentTradeRes(recentUser.name, recentAccount.acNo, recentCorporation.cpName, false, recentCorporation.cpLogo, recentHistory.tdDt))
                    checkBookmarkList.add(recentAccount.acNo)
                }
            }
        }
        return accountDetailList
    }

    override fun postRemit(remitInfoReq: RemitInfoReq) {
        val value = remitInfoReq.value  // ?????? ??????
        val date = LocalDateTime.now()  // ?????? ??????
        val remitTarget = remitInfoReq.acTag    // ?????? ?????? ?????? ??????
        val targetAccount = remitInfoReq.acReceive  // ?????? ?????? ?????? ??????

        val remitAccount = accountRepository.findById(remitInfoReq.acSend).orElse(null)?: throw NoAccountException()// ?????? ?????? ?????? ??????

        val realRemitAccount = accountRepository.findById(targetAccount).orElse(null)?: throw NoAccountException()
        val remitName = userRepository.findById(realRemitAccount.user.id).orElse(null).name


        val depositName = userRepository.findById(remitAccount.user.id).orElse(null).name

        if (remitAccount.balance < value){ throw InsufficientBalanceException()} // ?????? ????????? 418 ??????

        if (remitAccount.acNo == targetAccount){ throw RemitFailedException() } // ????????? ?????? ?????? ?????? ????????? ?????? ??????

        // ?????? ?????? ??????
        val tradeRemitHistory = TradeHistory(remitName,value, date, 2, remitTarget, targetAccount, remitInfoReq.receive, remitAccount.user.name, remitAccount)
        tradeHistoryRepository.save(tradeRemitHistory)
        // ?????? ?????? ??????
        remitAccount.withdraw(value)
        accountRepository.save(remitAccount)

        // ?????? ?????? ??????
        val depositAccount = accountRepository.findById(remitInfoReq.acReceive).orElse(null)?: throw NoAccountException() // ?????? ?????? ?????? ??????
        val depositRemitHistory = TradeHistory(depositName,value, date, 1, remitInfoReq.acName, remitInfoReq.acSend, remitAccount.user.name, remitInfoReq.receive, depositAccount)
        tradeHistoryRepository.save(depositRemitHistory)
        noticeService.sendAlarm(depositAccount.user.notice, "SOL#","${remitAccount.user.name}?????? ${DecimalFormat("#,###").format(value)}?????? ???????????????")
        // ?????? ?????? ??????
        depositAccount.deposit(value)
        accountRepository.save(depositAccount)

    }

    override fun postRemitPhone(remitPhoneReq: RemitPhoneReq) {
        val phone = remitPhoneReq.phone.replace("-", "") // ??? ??????
        val value = remitPhoneReq.value // ?????? ??????
        val date = LocalDateTime.now()  // ?????? ??????
        val user = userRepository.findByPhone(phone)
        if (userRepository.existsByPhone(phone) && user!!.type == "??????"){
            val user = userRepository.findByPhone(phone)!! // ??? ??????

            val userAccount = accountRepository.findByAcNo(user.account?: throw NoAccountException())?: throw NoAccountException()// ?????? ?????? ????????? 404 ??????
            val userCorporation = corporationRepository.findByCpCode(userAccount.acCpCode!!)!!

            val remitTarget = userCorporation.cpName
            val targetAccount = userAccount.acNo
            val send = remitPhoneReq.send
            val receive = remitPhoneReq.receive
            val remitAccount = accountRepository.findById(remitPhoneReq.acSend).orElse(null)?: throw NoAccountException()

            if (remitAccount.balance < value){ throw InsufficientBalanceException()} // ?????? ????????? 418 ??????

            if (remitAccount.acNo == targetAccount){ throw RemitFailedException() } // ????????? ?????? ?????? ?????? ????????? ?????? ??????

            val realRemitAccount = accountRepository.findById(targetAccount).orElse(null)?: throw NoAccountException()

            val remitName = userRepository.findById(realRemitAccount.user.id).orElse(null).name
            val depositName = userRepository.findById(remitAccount.user.id).orElse(null).name

            // ?????? ?????? ??????
            val tradeRemitHistory = TradeHistory(remitName,value, date, 2, remitTarget, targetAccount, receive, send, remitAccount)
            tradeHistoryRepository.save(tradeRemitHistory)
            // ?????? ?????? ??????
            remitAccount.withdraw(value)
            accountRepository.save(remitAccount)


            // ?????? ?????? ??????
            val depositAccount = accountRepository.findById(targetAccount).orElse(null)?: throw NoAccountException()
            val depositRemitHistory = TradeHistory(depositName, value, date, 1, remitPhoneReq.acName, remitPhoneReq.acSend, send, receive, depositAccount)
            tradeHistoryRepository.save(depositRemitHistory)
            noticeService.sendAlarm(depositAccount.user.notice,"SOL#", "${remitAccount.user.name}?????? ${DecimalFormat("#,###").format(value)}?????? ???????????????")
            // ?????? ?????? ??????
            depositAccount.deposit(value)
            accountRepository.save(depositAccount)
        }
        // ???????????????
        else {
            val remitAvailable = RemitAvailable(true, remitPhoneReq.acSend, remitPhoneReq.value)
            remitAvailableRepository.save(remitAvailable)
            throw NonMemberException(remitAvailable.tokenId.toString())
        }
    }

    override fun putBookmark(acNo: String, token: String) {
        val user: User
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()}) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            user = userRepository.findById(userId).get()
            if (bookmarkRepository.existsByUserIdAndAcNo(userId, acNo)){
                var bookmark = bookmarkRepository.findByUserIdAndAcNo(userId, acNo)
                bookmark.isClick()
                bookmarkRepository.save(bookmark)
            }else{
                var bookmark = Bookmark(acNo, user, true)
                bookmarkRepository.save(bookmark)
            }
        }

    }

    override fun postRemitPhoneNonMember(remitNonMemberReq: RemitNonMemberReq) {
        val remitInfoReq = remitNonMemberReq.remitInfoReq
        val remitAvailableRes = remitNonMemberReq.remitAvailableRes

        // ??? ?????? ????????? db ?????? ????????????
        val remitAvailable = remitAvailableRepository.findById(remitAvailableRes.tokenId).orElse(null)?: throw NoPhoneTokenException()
        // ????????? true ?????? ??? false??? ??????
        remitAvailable.check()

        val value = remitInfoReq.value  // ?????? ??????
        val date = LocalDateTime.now()  // ?????? ??????
        val remitTarget = remitInfoReq.acTag    // ?????? ?????? ?????? ??????
        val targetAccount = remitInfoReq.acReceive  // ?????? ?????? ?????? ??????
        val remitAccount = accountRepository.findById(remitInfoReq.acSend).orElse(null)?: throw NoAccountException()// ?????? ?????? ?????? ??????

        if (remitAccount.balance < value){ throw InsufficientBalanceException()} // ?????? ????????? 418 ??????

        if (remitAccount.acNo == targetAccount){ throw RemitFailedException() } // ????????? ?????? ?????? ?????? ????????? ?????? ??????

        // ?????? ?????? ??????
        val tradeRemitHistory = TradeHistory("?????????",value, date, 2, remitTarget, targetAccount, remitTarget, remitInfoReq.send, remitAccount)
        tradeHistoryRepository.save(tradeRemitHistory)
        // ?????? ?????? ??????
        remitAccount.withdraw(value)
        accountRepository.save(remitAccount)
        remitAvailableRepository.save(remitAvailable)
    }

    override fun getRemitPhoneNonMember(tokenId: Long): RemitTokenRes {
        val remitAvailable = remitAvailableRepository.findById(tokenId).orElse(null)?:throw NoPhoneTokenException()
        val account : Account = accountRepository.findByAcNo(remitAvailable.account) ?: throw NoAccountException()
        return remitAvailable.toEntity(account)
    }
}