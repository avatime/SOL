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
        val userRepository: UserRepository
) : RemitService {

    override fun getRecommendationAccount(token: String): List<RecentTradeRes> {
        val accountDetailList = ArrayList<RecentTradeRes>()

        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()
                }){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))

            // 사용자의 계좌 정보
            val accountList = accountRepository.findByUserId(userId).orEmpty()

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
                if (!myAccountList.contains(bookmark.tdTgAc) && !checkBookmarkList.contains(bookmark.tdTgAc)){
                    val bookmarkAccount = accountRepository.findById(bookmark.tdTgAc!!).orElse(null)?: throw NoAccountException()
                    val bookmarkUser = userRepository.findById(bookmarkAccount.user.id).orElse(null)
                    val bookmarkCorporation = corporationRepository.findById(bookmarkAccount.acCpCode).orElse(null)
                    accountDetailList.add(RecentTradeRes(bookmarkUser.name, bookmark.tdTgAc!!, bookmarkCorporation.cpName, true, bookmarkCorporation.cpLogo, bookmark.tdDt))
                    checkBookmarkList.add(bookmark.tdTgAc!!)
                }
            }

            // 최근 거래 계좌 추가
            val recentAccountList = tradeHistoryRepository.getTradeHistoriesByUserId(userId).orEmpty()
            for (recentHistory in recentAccountList){
                if (!myAccountList.contains(recentHistory.tdTgAc) && !checkBookmarkList.contains(recentHistory.tdTgAc)){
                    val recentAccount = accountRepository.findById(recentHistory.tdTgAc!!).orElse(null)?: throw NoAccountException()
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
        val value = remitInfoReq.value  // 이체 금액
        val date = LocalDateTime.now()  // 이체 일자
        val remitTarget = remitInfoReq.acTag    // 입금 받는 계좌 은행
        val targetAccount = remitInfoReq.acReceive  // 입금 받는 계좌 번호

        val remitAccount = accountRepository.findById(remitInfoReq.acSend).orElse(null)?: throw NoAccountException()// 송금 하는 계좌 객체

        val realRemitAccount = accountRepository.findById(targetAccount).orElse(null)?: throw NoAccountException()
        val remitName = userRepository.findById(realRemitAccount.user.id).orElse(null).name


        val depositName = userRepository.findById(remitAccount.user.id).orElse(null).name

        if (remitAccount.balance < value){ throw InsufficientBalanceException()} // 잔액 부족시 418 에러

        if (remitAccount.acNo == targetAccount){ throw RemitFailedException() } // 보내는 계좌 받는 계좌 같으면 예외 처리

        // 출금 거래 내역
        val tradeRemitHistory = TradeHistory(remitName,value, date, 2, remitTarget, targetAccount, remitInfoReq.receive, remitAccount.user.name, remitAccount)
        tradeHistoryRepository.save(tradeRemitHistory)
        // 잔액 변경 저장
        remitAccount.withdraw(value)
        accountRepository.save(remitAccount)

        // 입금 거래 내역
        val depositAccount = accountRepository.findById(remitInfoReq.acReceive).orElse(null)?: throw NoAccountException() // 입금 받는 계좌 객체
        val depositRemitHistory = TradeHistory(depositName,value, date, 1, remitInfoReq.acName, remitInfoReq.acSend, remitAccount.user.name, remitInfoReq.receive, depositAccount)
        tradeHistoryRepository.save(depositRemitHistory)
        // 잔액 변경 저장
        depositAccount.deposit(value)
        accountRepository.save(depositAccount)

    }

    override fun postRemitPhone(remitPhoneReq: RemitPhoneReq) {
        val phone = remitPhoneReq.phone.replace("-", "") // 폰 번호
        val value = remitPhoneReq.value // 이체 금액
        val date = LocalDateTime.now()  // 이체 일자

        if (userRepository.existsByPhone(phone)){
            val user = userRepository.findByPhone(phone)!! // 폰 주인

            val userAccount = accountRepository.findByAcNo(user.account?: throw NoAccountException())?: throw NoAccountException()// 대표 계좌 없으면 404 반환
            val userCorporation = corporationRepository.findByCpCode(userAccount.acCpCode!!)!!

            val remitTarget = userCorporation.cpName
            val targetAccount = userAccount.acNo
            val send = remitPhoneReq.send
            val receive = remitPhoneReq.receive
            val remitAccount = accountRepository.findById(remitPhoneReq.acSend).orElse(null)?: throw NoAccountException()

            if (remitAccount.balance < value){ throw InsufficientBalanceException()} // 잔액 부족시 418 에러

            if (remitAccount.acNo == targetAccount){ throw RemitFailedException() } // 보내는 계좌 받는 계좌 같으면 예외 처리

            val realRemitAccount = accountRepository.findById(targetAccount).orElse(null)?: throw NoAccountException()

            val remitName = userRepository.findById(realRemitAccount.user.id).orElse(null).name
            val depositName = userRepository.findById(remitAccount.user.id).orElse(null).name

            // 출금 거래 내역
            val tradeRemitHistory = TradeHistory(remitName,value, date, 2, remitTarget, targetAccount, receive, send, remitAccount)
            tradeHistoryRepository.save(tradeRemitHistory)
            // 잔액 변경 저장
            remitAccount.withdraw(value)
            accountRepository.save(remitAccount)


            // 입금 거래 내역
            val depositAccount = accountRepository.findById(targetAccount).orElse(null)?: throw NoAccountException()
            val depositRemitHistory = TradeHistory(depositName, value, date, 1, remitPhoneReq.acName, remitPhoneReq.acSend, send, receive, depositAccount)
            tradeHistoryRepository.save(depositRemitHistory)
            // 잔액 변경 저장
            depositAccount.deposit(value)
            accountRepository.save(depositAccount)
        }
        // 비회원이면
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

        // 폰 토큰 가지고 db 객체 가져오기
        val remitAvailable = remitAvailableRepository.findById(remitAvailableRes.tokenId).orElse(null)?: throw NoPhoneTokenException()
        // 기존의 true 였던 걸 false로 변경
        remitAvailable.check()

        val value = remitInfoReq.value  // 이체 금액
        val date = LocalDateTime.now()  // 이체 일자
        val remitTarget = remitInfoReq.acTag    // 입금 받는 계좌 은행
        val targetAccount = remitInfoReq.acReceive  // 입금 받는 계좌 번호
        val remitAccount = accountRepository.findById(remitInfoReq.acSend).orElse(null)?: throw NoAccountException()// 송금 하는 계좌 객체

        if (remitAccount.balance < value){ throw InsufficientBalanceException()} // 잔액 부족시 418 에러

        if (remitAccount.acNo == targetAccount){ throw RemitFailedException() } // 보내는 계좌 받는 계좌 같으면 예외 처리

        // 출금 거래 내역
        val tradeRemitHistory = TradeHistory("비회원",value, date, 2, remitTarget, targetAccount, remitTarget, remitInfoReq.send, remitAccount)
        tradeHistoryRepository.save(tradeRemitHistory)
        // 잔액 변경 저장
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