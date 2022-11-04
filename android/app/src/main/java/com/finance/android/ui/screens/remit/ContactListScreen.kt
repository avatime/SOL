package com.finance.android.ui.screens.remit


import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract.Contacts
import android.util.Log
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.finance.android.ui.components.ContactItem
import com.finance.android.utils.retrieveAllContacts
import com.finance.android.viewmodels.RemitViewModel
import kotlinx.coroutines.coroutineScope


@Composable
fun ContactListScreen(modifier: Modifier = Modifier.background(Color.White) ,remitViewModel: RemitViewModel,navController: NavController) {
    remitViewModel.requestRemit.value = true
    if (ActivityCompat.checkSelfPermission(
            LocalContext.current,
            Manifest.permission.READ_CONTACTS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }


    val list = LocalContext.current.retrieveAllContacts()
    Log.i("TEST", list.toString())
    Box(Modifier.fillMaxSize()) {

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.padding(10.dp))

            list.forEach{
                 ContactItem(name = it.name, number = it.phoneNumber[0], avatar = it.avatar, modifier = Modifier, remitViewModel = remitViewModel,navController= navController) }

        }


    }


}


