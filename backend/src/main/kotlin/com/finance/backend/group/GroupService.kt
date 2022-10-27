package com.finance.backend.group

import com.finance.backend.group.request.RegistPublicAccountReq
import com.finance.backend.group.response.FriendRes
import com.finance.backend.group.response.PublicAccountRes

interface GroupService {
    fun getAllGroups(accessToken : String) : List<PublicAccountRes>
    fun createNewGroup(accessToken: String, registPublicAccountReq: RegistPublicAccountReq)
    fun disableExistGroup(accessToken: String, publicAccountId: Long)
    fun getAllMembersInGroups(accessToken: String, publicAccountId: Long) : List<FriendRes>
}