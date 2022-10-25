package com.finance.backend.group

import com.finance.backend.group.entity.PublicAccount
import com.finance.backend.group.request.RegistPublicAccountReq
import com.finance.backend.group.response.PublicAccountRes

interface GroupService {
    fun getAllGroups(accessToken : String) : List<PublicAccountRes>
    fun createNewGroup(accessToken: String, registPublicAccountReq: RegistPublicAccountReq)
}