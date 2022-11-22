package com.finance.android.ui.screens.groupAccount

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.finance.android.ui.components.AnimatedLoading
import com.finance.android.ui.components.GroupAccountMemberListItem
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.viewmodels.GroupAccountViewModel
import dagger.hilt.android.internal.Contexts.getApplication
import java.text.DecimalFormat
import java.util.*

@Composable
fun GroupAccountMemberScreen(
    groupAccountViewModel: GroupAccountViewModel
) {
    val context = LocalContext.current
    fun launch() {
        groupAccountViewModel.getGroupAccountMember(groupAccountViewModel.paId.value)
    }

    LaunchedEffect(Unit) {
        launch()
    }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        when (val response = groupAccountViewModel.groupAccountMemberData.value) {
            is Response.Failure -> Text(text = "실패")
            is Response.Loading -> AnimatedLoading(text = "가져오고 있어요")
            is Response.Success -> {
                LazyColumn {
                    items(count = response.data.size, key = { it }, itemContent = {
                        val item = response.data[it]
                        GroupAccountMemberListItem(
                            img = item.pfImg,
                            name = item.userName,
                            type = item.type,
                            phoneNumber = item.phone,
                            onClickNonMember = {
                                sendInviteMessage(context= context, phoneNumber = item.phone)
                            }
                        )
                    })
                }
            }
        }
    }

}
fun sendInviteMessage(context: Context, phoneNumber: String) {
    val link =
        "${Const.WEB_API}"

    val smsUri = Uri.parse("sms:${phoneNumber.replace("-", "")}")
    val sendIntent = Intent(Intent.ACTION_SENDTO, smsUri).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        putExtra(
            "sms_body",
            "아래 링크로 접속해서 앱을 다운로드 받은 후 모두의 통장을 사용해보세요!.\n$link"
        )
    }
    context.startActivity(sendIntent)


}
