package com.finance.android.ui.screens.groupAccount

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.finance.android.domain.dto.response.BankAccountResponseDto
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.FriendSelectItem
import com.finance.android.ui.components.SelectedFriendItem
import com.finance.android.ui.components.TextButton
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.utils.retrieveAllContacts
import com.finance.android.viewmodels.GroupAccountViewModel

@Composable
fun ContactFriendListScreen(
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

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            friendsList.forEach {
                SelectedFriendItem(img = it.avatar, name = it.name)
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
            onClick = { navController.navigate(Const.GROUP_ACCOUNT_COMPLETED) },
            modifier = Modifier
                .withBottomButton(),
            text = "모임 통장 생성하기",
            buttonType = ButtonType.ROUNDED
        )
    }

}



