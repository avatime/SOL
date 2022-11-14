package com.finance.android.ui.screens.groupAccount

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.finance.android.domain.dto.request.CreateDuesRequestDto
import com.finance.android.domain.dto.request.MemberRequestDto
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.FriendSelectItem
import com.finance.android.ui.components.SelectedFriendItem
import com.finance.android.ui.components.TextButton
import com.finance.android.utils.Const
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.utils.retrieveAllContacts
import com.finance.android.viewmodels.GroupAccountViewModel
import java.lang.Integer.parseInt

@Composable
fun DuesMemberListScreen(
    groupAccountViewModel: GroupAccountViewModel,
    navController: NavController,
    modifier: Modifier,
) {

    if (ActivityCompat.checkSelfPermission(
            LocalContext.current,
            Manifest.permission.READ_CONTACTS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }
    val list = LocalContext.current.retrieveAllContacts()
    Log.i("TEST", "${list.size}")
    groupAccountViewModel.initSelectedFriendsList(list.size)

    val friendsList =
        list.filterIndexed { idx, _ -> groupAccountViewModel.selectFriendsList!![idx].value }

    for (friend in friendsList) {
        Log.i("친구 번호", "${friend.phoneNumber}")
    }

    groupAccountViewModel.name.value
    val memberList = ArrayList<MemberRequestDto>()
    for (friend in friendsList) {
        memberList.add(MemberRequestDto(friend.name, friend.phoneNumber.toString()))
    }

    val createDuesRequestDto = CreateDuesRequestDto(
        groupAccountViewModel.duesName.value,
        groupAccountViewModel.paId.value,
        parseInt(groupAccountViewModel.duesBalance.value),
        duesDue = "${groupAccountViewModel.mDate.value} ${groupAccountViewModel.mTime.value}",
        memberList
    )


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
                    Log.i("gg", "${item.contactId}")
                    SelectedFriendItem(
                        img = item.avatar,
                        name = item.name,
                        onClick = {
                            val index =
                                list.indexOfFirst { data -> data.contactId == item.contactId }
                            groupAccountViewModel.onClickDeleteFriend(index)
                        })


                })
            }
        }


        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(count = list.size, key = { it }, itemContent = {
                val item = list[it]
                FriendSelectItem(
                    checked = groupAccountViewModel.selectFriendsList!![it].value,
                    img = item.avatar,
                    name = item.name,
                    phone = item.phoneNumber[0],
                    onClickItem = { groupAccountViewModel.onClickFriend(it) })
            }
            )

        }

        TextButton(
            onClick = {
                groupAccountViewModel.makeGroupDues(
                    createDuesRequestDto,
                    onSuccess = { navController.navigate(Const.GROUP_ACCOUNT_COMPLETED) })
            },
            modifier = Modifier
                .withBottomButton(),
            text = "회비 생성하기",
            buttonType = ButtonType.ROUNDED
        )
    }

}