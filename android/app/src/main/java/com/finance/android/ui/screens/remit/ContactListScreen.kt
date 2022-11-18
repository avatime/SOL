package com.finance.android.ui.screens.remit

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.compose.collectAsLazyPagingItems
import com.finance.android.ui.components.AnimatedLoading
import com.finance.android.ui.components.ContactItem
import com.finance.android.utils.ContactSource
import com.finance.android.viewmodels.RemitViewModel

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
    val list = remember {
        Pager(PagingConfig(pageSize = 30)) {
            ContactSource(context)
        }.flow
    }
    val lazyItems = list.collectAsLazyPagingItems()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
    ) {
        LazyColumn {
            items(count = lazyItems.itemCount) { idx ->
                val item = lazyItems[idx]
                item?.let {
                    ContactItem(
                        name = it.name,
                        number = it.phoneNumber,
                        avatar = it.avatar,
                        remitViewModel = remitViewModel,
                        navController = navController
                    )
                }
            }
        }

        if (lazyItems.loadState.refresh is LoadState.Loading) {
            AnimatedLoading()
        }
    }
}
