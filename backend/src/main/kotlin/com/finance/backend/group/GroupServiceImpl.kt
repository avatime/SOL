package com.finance.backend.group

import com.finance.backend.Exceptions.InvalidUserException
import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.daily.entity.Attendance
import com.finance.backend.group.entity.PublicAccount
import com.finance.backend.group.entity.PublicAccountMember
import com.finance.backend.group.repository.DuesRepository
import com.finance.backend.group.repository.PublicAccountMemberRepository
import com.finance.backend.group.repository.PublicAccountRepository
import com.finance.backend.group.repository.UserDuesRelationRepository
import com.finance.backend.group.request.MemberInfoReq
import com.finance.backend.group.request.RegistPublicAccountReq
import com.finance.backend.group.response.FriendRes
import com.finance.backend.group.response.PublicAccountRes
import com.finance.backend.profile.ProfileRepository
import com.finance.backend.user.User
import com.finance.backend.user.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDateTime
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
        private val jwtUtils: JwtUtils
) : GroupService {

    fun saveAsNonMember() {
//        val user : User = User()
    }

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
            val user : User = userRepository.findById(userId).orElse(null) ?: throw InvalidUserException()
            val publicAccount: PublicAccount = publicAccountRepository.findById(publicAccountId).orElse(throw NullPointerException())
            if(!publicAccountMemberRepository.existsByUserAndPublicAccountAndType(user, publicAccount, "관리자")) throw AuthenticationException()
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

    fun registMembers(list : List<MemberInfoReq>, publicAccount : PublicAccount){
        for(member in list) {
            val user : User = userRepository.findByPhone(member.phone) ?: userRepository.save(User(member.name, "password", member.phone, Timestamp.valueOf(LocalDateTime.now()), 0, "비회원"))
            publicAccountMemberRepository.save(PublicAccountMember(publicAccount, user, user.type))
        }
    }
}