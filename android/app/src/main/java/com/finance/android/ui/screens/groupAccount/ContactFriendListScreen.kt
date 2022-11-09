package com.finance.android.ui.screens.groupAccount

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.finance.android.ui.components.FriendSelectItem
import com.finance.android.utils.retrieveAllContacts
import com.finance.android.viewmodels.GroupAccountViewModel

@Composable
fun ContactFriendListScreen(groupAccountViewModel: GroupAccountViewModel) {
    if (ActivityCompat.checkSelfPermission(
            LocalContext.current,
            Manifest.permission.READ_CONTACTS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }


    val list = LocalContext.current.retrieveAllContacts()

    LazyColumn(){
        items(count = list.size,
        key={it},
        itemContent = {
            val item = list[it]
            FriendSelectItem(checked = groupAccountViewModel.isSelect.value, img = item.avatar, name = item.name, phone = item.phoneNumber[0]) {

            }
        })
    }
}