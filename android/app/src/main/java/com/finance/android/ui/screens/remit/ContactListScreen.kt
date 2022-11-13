package com.finance.android.ui.screens.remit


import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.finance.android.domain.dto.response.ContactDto
import com.finance.android.ui.components.ContactItem
import com.finance.android.utils.retrieveAllContacts
import com.finance.android.viewmodels.RemitViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun ContactListScreen(
    modifier: Modifier = Modifier.background(Color.White),
    remitViewModel: RemitViewModel,
    navController: NavController
) {
    remitViewModel.requestRemit.value = true
    if (ActivityCompat.checkSelfPermission(
            LocalContext.current,
            Manifest.permission.READ_CONTACTS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }


    val context = LocalContext.current
    val list = remember { mutableStateOf<List<ContactDto>>(emptyList()) }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            list.value = context.retrieveAllContacts().filter { it.phoneNumber.isNotEmpty() }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        LazyColumn {
            items(count = list.value.size) { idx ->
                val it = list.value[idx]
                ContactItem(
                    name = it.name,
                    number = it.phoneNumber[0],
                    avatar = it.avatar,
                    modifier = Modifier,
                    remitViewModel = remitViewModel,
                    navController = navController
                )
            }
        }


    }


}


