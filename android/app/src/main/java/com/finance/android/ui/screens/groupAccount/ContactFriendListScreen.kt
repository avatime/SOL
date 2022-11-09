package com.finance.android.ui.screens.groupAccount

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.FriendSelectItem
import com.finance.android.ui.components.TextButton
import com.finance.android.utils.Const
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.utils.retrieveAllContacts
import com.finance.android.viewmodels.GroupAccountViewModel

@Composable
fun ContactFriendListScreen(
    groupAccountViewModel: GroupAccountViewModel,
    navController: NavController,
    modifier: Modifier
) {
    if (ActivityCompat.checkSelfPermission(
            LocalContext.current,
            Manifest.permission.READ_CONTACTS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }


    val list = LocalContext.current.retrieveAllContacts()
    Column(modifier = modifier) {
        LazyColumn() {
            items(count = list.size,
                key = { it },
                itemContent = {
                    val item = list[it]
                    FriendSelectItem(
                        checked = groupAccountViewModel.isSelect.value,
                        img = item.avatar,
                        name = item.name,
                        phone = item.phoneNumber[0]
                    ) {

                    }
                })

        }
        TextButton(
            onClick = { navController.navigate(Const.GROUP_ACCOUNT_COMPLETED) },
            modifier = modifier
                .withBottomButton()
                .padding(end = 5.dp),
            text = "모임 통장 생성하기",
            buttonType = ButtonType.ROUNDED
        )
    }


}