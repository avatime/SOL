package com.finance.backend.group

import com.finance.backend.group.request.DuesPayReq
import com.finance.backend.group.request.RegistDueReq
import com.finance.backend.group.request.RegistPublicAccountReq
import com.finance.backend.group.response.*

interface GroupService {
    fun getAllGroups(accessToken : String) : List<PublicAccountRes>
    fun createNewGroup(accessToken: String, registPublicAccountReq: RegistPublicAccountReq)
    fun disableExistGroup(accessToken: String, publicAccountId: Long)
    fun getAllMembersInGroups(accessToken: String, publicAccountId: Long) : List<FriendRes>?
    fun getAllTradeHistory(accessToken: String, publicAccountId: Long) : List<PublicTradeRes>?
    fun getAllDues(accessToken: String, publicAccountId: Long) : List<DuesRes>?
    fun getDueDetails(accessToken: String, dueId : Long) : DuesDetailsRes?
    fun payDue(accessToken: String, duesPayReq: DuesPayReq)
    fun createDue(accessToken: String, registDueReq: RegistDueReq)
}