package com.finance.android.ui.screens.groupAccount

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.finance.android.domain.dto.request.CreateDuesRequestDto
import com.finance.android.domain.dto.request.CreateGroupAccountRequestDto
import com.finance.android.domain.dto.request.MemberRequestDto
import com.finance.android.domain.dto.response.FriendResponseDto
import com.finance.android.ui.GroupAccountEmpty
import com.finance.android.ui.components.*
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.utils.retrieveAllContacts
import com.finance.android.viewmodels.GroupAccountViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DuesMemberListScreen(
    groupAccountViewModel: GroupAccountViewModel,
    navController: NavController,
    modifier: Modifier,
) {

    fun launch() {
        groupAccountViewModel.getGroupAccountMember(groupAccountViewModel.paId.value)

    }
    LaunchedEffect(Unit) {
        launch()
        groupAccountViewModel.selectFriendsList = null
    }

    when (val response = groupAccountViewModel.groupAccountMemberData.value) {
        is Response.Failure -> androidx.compose.material3.Text(text = "실패")
        is Response.Loading -> AnimatedLoading(text = "가져오고 있어요")
        is Response.Success -> {
            groupAccountViewModel.list = response.data
            groupAccountViewModel.initSelectedFriendsList(groupAccountViewModel.list.size)

            val friendsList = groupAccountViewModel.list.filterIndexed { idx, _ ->
                groupAccountViewModel.selectFriendsList!![idx].value
            }
            val memberList = ArrayList<MemberRequestDto>()
            for (friend in friendsList) {
                memberList.add(MemberRequestDto(friend.userName, friend.phone))
            }
            val name = groupAccountViewModel.duesName.value
            val paId = groupAccountViewModel.paId.value
            val duesVal = groupAccountViewModel.duesBalance.value.toInt()

            val createDuesRequestDto =
                CreateDuesRequestDto(name, paId, duesVal , groupAccountViewModel.mDate.value, memberList)
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                if (friendsList.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .height(130.dp)
                            .fillMaxWidth()
                    ) {
                        items(count = friendsList.size, key = { it }, itemContent = {
                            val item = friendsList[it]
                            Log.i("gg", "${item.id}")
                            SelectedFriendItem(img = item.pfImg, name = item.userName, onClick = {
                                val index =
                                    groupAccountViewModel.list.indexOfFirst { data -> data.id == item.id }
                                groupAccountViewModel.onClickDeleteFriend(index)
                            })
                        })
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    items(count = groupAccountViewModel.list.size, key = { it }, itemContent = {
                        val item = groupAccountViewModel.list[it]
                        FriendSelectItem(checked = groupAccountViewModel.selectFriendsList!![it].value,
                            img = item.pfImg,
                            name = item.userName,
                            phone = item.type,
                            onClickItem = { groupAccountViewModel.onClickFriend(it) })
                    })
                }
                TextButton(
                    onClick = {
                        groupAccountViewModel.makeGroupDues(createDuesRequestDto,
                            onSuccess = { navController.navigate(Const.GROUP_ACCOUNT_COMPLETED) })
                    },
                    modifier = Modifier.withBottomButton(),
                    text = "회비 생성하기",
                    buttonType = ButtonType.ROUNDED
                )
            }

        }

    }
}
