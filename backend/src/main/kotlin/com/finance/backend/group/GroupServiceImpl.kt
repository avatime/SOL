package com.finance.backend.group

import com.finance.backend.Exceptions.*
import com.finance.backend.bank.Account
import com.finance.backend.bank.AccountRepository
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.group.entity.Dues
import com.finance.backend.group.entity.PublicAccount
import com.finance.backend.group.entity.PublicAccountMember
import com.finance.backend.group.entity.UserDuesRelation
import com.finance.backend.group.repository.DuesRepository
import com.finance.backend.group.repository.PublicAccountMemberRepository
import com.finance.backend.group.repository.PublicAccountRepository
import com.finance.backend.group.repository.UserDuesRelationRepository
import com.finance.backend.group.request.DuesPayReq
import com.finance.backend.group.request.MemberInfoReq
import com.finance.backend.group.request.RegistDueReq
import com.finance.backend.group.request.RegistPublicAccountReq
import com.finance.backend.group.response.*
import com.finance.backend.profile.ProfileRepository
import com.finance.backend.tradeHistory.TradeHistory
import com.finance.backend.tradeHistory.TradeHistoryRepository
import com.finance.backend.user.User
import com.finance.backend.user.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.naming.AuthenticationException

@Service("GroupService")
@RequiredArgsConstructor
class GroupServiceImpl (
        private val duesRepository: DuesRepository,
        private val publicAccountRepository: PublicAccountRepository,
        private val publicAccountMemberRepository: PublicAccountMemberRepository,
        private val userDuesRelationRepository: UserDuesRelationRepository,
        private val userRepository: UserRepository,
        private val profileRepository: ProfileRepository,
        private val tradeHistoryRepository: TradeHistoryRepository,
        private val accountRepository: AccountRepository,
        private val jwtUtils: JwtUtils
) : GroupService {

    override fun getAllGroups(accessToken: String): List<PublicAccountRes> {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException() }){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            val list : List<PublicAccountMember> = publicAccountMemberRepository.findAllByUserAndPublicAccount_PaStatus(user, 10)?: emptyList()
            return List(list.size) {i -> list[i].publicAccount.toEntity()}
        } else throw Exception()
    }

    override fun createNewGroup(accessToken: String, registPublicAccountReq: RegistPublicAccountReq) {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException() }){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElse(null) ?: throw InvalidUserException()
            var publicAccount : PublicAccount = PublicAccount(registPublicAccountReq.name)
            publicAccount = publicAccountRepository.save(publicAccount)
            publicAccountMemberRepository.save(PublicAccountMember(publicAccount, user, "관리자"))
            registPublicAccountReq.memberList?.let { registMembers(it, publicAccount) }
        }
    }

    override fun disableExistGroup(accessToken: String, publicAccountId: Long) {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException() }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val publicAccount: PublicAccount = publicAccountRepository.findById(publicAccountId).orElse(throw NullPointerException())
            if(!publicAccountMemberRepository.existsByUserAndPublicAccountAndType(userRepository.findById(userId).orElse(null) ?: throw InvalidUserException(), publicAccount, "관리자")) throw AuthenticationException()
            publicAccount.terminate()
            publicAccountRepository.save(publicAccount)
        }
    }

    override fun getAllMembersInGroups(accessToken: String, publicAccountId: Long): List<FriendRes> {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException() }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElse(null) ?: throw InvalidUserException()
            if(!publicAccountMemberRepository.existsByUserAndPublicAccountId(user, publicAccountId)) throw AuthenticationException()
            val memberList : List<PublicAccountMember> = publicAccountMemberRepository.findAllByPublicAccountId(publicAccountId)?:throw Exception()
            return List(memberList.size) {i -> memberList[i].toEntity(profileRepository.findByPfId(memberList[i].user.pfId)?:throw NullPointerException())}
        } else throw Exception()
    }

    override fun getAllTradeHistory(accessToken: String, publicAccountId: Long): List<PublicTradeRes>? {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException() }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElse(null) ?: throw InvalidUserException()
            if(!publicAccountMemberRepository.existsByUserAndPublicAccountId(user, publicAccountId)) throw AuthenticationException()
            val tradeList : List<TradeHistory> = tradeHistoryRepository.findAllByTdTgAc("모임통장 $publicAccountId") ?:throw Exception()
            return List(tradeList.size) {i -> tradeList[i].toEntity()}
        } else throw Exception()
    }

    override fun getAllDues(accessToken: String, publicAccountId: Long): List<DuesRes>? {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException() }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElse(null) ?: throw InvalidUserException()
            if(!publicAccountMemberRepository.existsByUserAndPublicAccountId(user, publicAccountId)) throw AuthenticationException()
            val dueList : List<Dues> = duesRepository.findAllByPublicAccountIdAndStatus(publicAccountId, 10)?:throw Exception()
            return List(dueList.size) {i -> dueList[i].toEntity(userDuesRelationRepository.findByUserAndDues(user, dueList[i])?.status?: throw Exception(), userDuesRelationRepository.countByDuesAndStatus(dueList[i], true), userDuesRelationRepository.countByDues(dueList[i]), userRepository.findById(dueList[i].creator).orElse(null)?.name?:throw NullPointerException())}
        } else throw Exception()
    }

    override fun getDueDetails(accessToken: String, dueId: Long): DuesDetailsRes? {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException() }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElse(null) ?: throw InvalidUserException()
            val due : Dues = userDuesRelationRepository.findByUserAndId(user, dueId)?.dues ?: throw AuthenticationException()
            if(due.status == 99) throw DuesNotExistsException()
            val memberList : List<UserDuesRelation> = userDuesRelationRepository.findAllByDues(due)?:throw Exception()
            return DuesDetailsRes(due.duesName, due.duesVal, List(memberList.size) {i -> memberList[i].toEntity(profileRepository.getReferenceById(user.pfId))}, due.creator == userId || publicAccountMemberRepository.existsByUserAndPublicAccountAndType(user, due.publicAccount, "관리자"))
        } else throw Exception()
    }

    override fun payDue(accessToken: String, duesPayReq: DuesPayReq) {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException() }) {
            val userId: UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user: User = userRepository.findById(userId).orElse(null) ?: throw InvalidUserException()
            val account : Account = accountRepository.findByAcNoAndUser(duesPayReq.acNo, user) ?: throw AccountNotSubToUserException()
            val userDuesRelation : UserDuesRelation = userDuesRelationRepository.findByUserAndDuesId(user, duesPayReq.duesId) ?: throw AuthenticationException()
            if(userDuesRelation.dues.status == 99) throw DuesNotExistsException()
            if(duesPayReq.duesVal != userDuesRelation.dues.duesVal) throw WrongAmountException()
            if(account.balance < duesPayReq.duesVal) throw InsufficientBalanceException()
            userDuesRelation.paid()
            account.withdraw(duesPayReq.duesVal)
            accountRepository.save(account)
            tradeHistoryRepository.save(TradeHistory(duesPayReq.duesVal, userDuesRelation.dueDate!!, 2, userDuesRelation.dues.duesName, "모임통장 " + userDuesRelation.dues.publicAccount.id, userDuesRelation.dues.publicAccount.paName,user.name, account))
            userDuesRelation.dues.publicAccount.addPaVal(duesPayReq.duesVal)
            publicAccountRepository.save(userDuesRelation.dues.publicAccount)

            userDuesRelationRepository.save(userDuesRelation)
        }
    }

    override fun createDue(accessToken: String, registDueReq: RegistDueReq) {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException() }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElse(null) ?: throw InvalidUserException()
            val publicAccount : PublicAccount = publicAccountRepository.findById(registDueReq.paId).orElse(null) ?: throw AuthenticationException()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val date : LocalDateTime? = LocalDateTime.parse(registDueReq.duesDue, formatter);
            val dues : Dues = duesRepository.save(Dues(publicAccount, registDueReq.name, registDueReq.duesVal, if(date == null) 1 else 0, date, userId))

            for(member in registDueReq.memberList) {
                userDuesRelationRepository.save(UserDuesRelation(dues, user))
            }
        }
    }

    override fun disableExistDue(accessToken: String, dueId: Long) {
        if(try {jwtUtils.validation(accessToken)} catch (e: Exception) {throw TokenExpiredException() }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(accessToken))
            val user : User = userRepository.findById(userId).orElse(null) ?: throw InvalidUserException()
            val due : Dues = duesRepository.findByIdAndStatus(dueId, 10) ?: throw DuesNotExistsException()
            val userDuesRelation : UserDuesRelation = userDuesRelationRepository.findByUserAndDues(user, due) ?: throw AuthenticationException()
            if(due.creator != userId && !publicAccountMemberRepository.existsByUserAndPublicAccountAndType(user, due.publicAccount, "관리자")) throw AuthenticationException()
            due.disable()
            duesRepository.save(due)
        } else throw Exception()
    }

    fun registPaymentMonthly() {
        TODO("정기적으로 생성하는 코드 만들어야함")
    }

    fun registMembers(list : List<MemberInfoReq>, publicAccount : PublicAccount){
        for(member in list) {
            val user : User = userRepository.findByPhone(member.phone) ?: userRepository.save(User(member.name, "password", member.phone, Timestamp.valueOf(LocalDateTime.now()), 0, "비회원"))
            publicAccountMemberRepository.save(PublicAccountMember(publicAccount, user, user.type))
        }
    }
}