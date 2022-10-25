package com.finance.backend.bank;

import com.finance.backend.accountProduct.AccountProductRepository
import com.finance.backend.auth.Exceptions.TokenExpiredException
import com.finance.backend.bank.response.BankAccountRes
import com.finance.backend.bank.response.BankDetailRes
import com.finance.backend.bank.response.BankTradeRes
import com.finance.backend.bank.response.RecentTradeRes
import com.finance.backend.bookmark.Bookmark
import com.finance.backend.bookmark.BookmarkRepository
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.corporation.CorporationRepository
import com.finance.backend.tradeHistory.TradeHistoryRepository
import com.finance.backend.user.User
import com.finance.backend.user.UserRepository
import org.springframework.stereotype.Service;
import java.util.UUID

@Service("AccountService")
class AccountServiceImpl(
        val userRepository: UserRepository,
        val accountRepository: AccountRepository,
        val tradeHistoryRepository: TradeHistoryRepository,
        val bookmarkRepository: BookmarkRepository,
        val corporationRepository: CorporationRepository,
        val jwtUtils: JwtUtils
) : AccountService {

    override fun getAccountAll(token: String): List<BankAccountRes> {
        var bankAccountList = ArrayList<BankAccountRes>()
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException() }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val accountList = accountRepository.findByUserId(userId)
            for (ac in accountList){
                bankAccountList.add(BankAccountRes(ac.acNo, ac.balance, ac.acName))
            }
        }
        return bankAccountList
    }

    override fun registerAccount(acNo: String) {
        val account = accountRepository.findById(acNo).get()
        account.apply {
            acReg = !acReg!!
        }
        accountRepository.save(account)
    }

    override fun registerRemitAccount(acNo: String) {
        val account = accountRepository.findById(acNo).get()
        account.apply {
            acRmReg = !acRmReg!!
        }
        accountRepository.save(account)

//        val user = userRepository.findById(account.acNo)
//        user.apply {
//            account = account.acNo
//        }

    }

    override fun registerBookmarkAccount(acNo: String, token: String) {
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

    override fun getAccountDetail(acNo: String): BankDetailRes {
        var accountDetailList = ArrayList<BankTradeRes>()
        val account = accountRepository.findById(acNo)
        val bankAccountRes = BankAccountRes(account.get().acNo, account.get().balance, account.get().acName)
        val tradeHistroyList = tradeHistoryRepository.findAllByAccountAcNo(account.get().acNo)
        for (trade in tradeHistroyList){
            accountDetailList.add(BankTradeRes(trade.tdDt,trade.tdVal, trade.tdCn, trade.tdType))
        }
        val bankDetailRes = BankDetailRes(bankAccountRes, accountDetailList)
        return bankDetailRes
    }

    override fun getAccountDetailType(acNo: String, type: Int): List<BankTradeRes> {
        var tradeHistoryList = tradeHistoryRepository.findAllByAccountAcNoAndTypeOrderByTdDtDesc(acNo, type)
        var accountDetailList = ArrayList<BankTradeRes>()
        for (trade in tradeHistoryList){
            accountDetailList.add(BankTradeRes(trade.tdDt, trade.tdVal, trade.tdCn, trade.tdType))
        }
        return accountDetailList
    }

    override fun getRecentTrade(token: String): List<RecentTradeRes> {
        var accountDetailList = ArrayList<RecentTradeRes>()

        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()}){
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

    override fun getUserName(acNo: String, cpCode: Long): String {
        val account = accountRepository.findByAcNoAnAndAcCpCode(acNo, cpCode)?: let{return ""}
        val userName = userRepository.findById(account.user.id).get().name
        return userName
    }
}
