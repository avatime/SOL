package com.finance.android.ui.screens.groupAccount

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.finance.android.domain.dto.request.CreateDuesRequestDto
import com.finance.android.domain.dto.request.MemberRequestDto
import com.finance.android.ui.components.*
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.GroupAccountViewModel

@Composable
fun DuesMemberListScreen(
    groupAccountViewModel: GroupAccountViewModel,
    navController: NavController,
    modifier: Modifier
) {
    fun launch() {
        groupAccountViewModel.getGroupAccountMember(groupAccountViewModel.paId.value)
    }
    LaunchedEffect(Unit) {
        launch()
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
                CreateDuesRequestDto(
                    name,
                    paId,
                    duesVal,
                    groupAccountViewModel.mDate.value,
                    memberList
                )
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth().animateContentSize()
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

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    items(count = groupAccountViewModel.list.size, key = { it }, itemContent = {
                        val item = groupAccountViewModel.list[it]
                        FriendSelectItem(
                            checked = groupAccountViewModel.selectFriendsList!![it].value,
                            img = item.pfImg,
                            name = item.userName,
                            phone = item.type,
                            onClickItem = { groupAccountViewModel.onClickFriend(it) }
                        )
                    })
                }

                AnimatedVisibility(
                    visible = friendsList.isNotEmpty(),
                    enter = slideInVertically(initialOffsetY = { it / 2 }),
                    exit = slideOutVertically(targetOffsetY = { 2 * it })
                ) {
                    TextButton(
                        onClick = {
                            groupAccountViewModel.OKtext.value = "회비 생성 성공"
                            groupAccountViewModel.makeGroupDues(
                                createDuesRequestDto,
                                onSuccess = {
                                    navController.navigate(Const.GROUP_ACCOUNT_COMPLETED){
                                        popUpTo(Const.GROUP_ACCOUNT_DETAIL_SCREEN)
                                    }
                                    groupAccountViewModel.initList(groupAccountViewModel.list.size)
                                }
                            )
                        },
                        modifier = Modifier.withBottomButton(),
                        text = "회비 생성하기",
                        buttonType = ButtonType.ROUNDED
                    )
                }
            }
        }
    }
}
