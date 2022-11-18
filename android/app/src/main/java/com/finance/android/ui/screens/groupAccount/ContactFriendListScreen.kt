package com.finance.android.ui.screens.groupAccount

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.finance.android.domain.dto.response.ContactDto
import com.finance.android.ui.components.*
import com.finance.android.utils.Const
import com.finance.android.utils.ContactSource
import com.finance.android.utils.ext.withBottomButton
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

    val context = LocalContext.current
    val list = remember {
        Pager(PagingConfig(pageSize = 30)) {
            ContactSource(context)
        }.flow
    }
    val lazyItems = list.collectAsLazyPagingItems()

    Screen(
        modifier = modifier,
        selectedIdSet = groupAccountViewModel.selectedIdSet,
        selectedItemList = groupAccountViewModel.selectedContactList.value,
        lazyItemList = lazyItems,
        onClickSelectedItem = {
            groupAccountViewModel.onClickSelectedContact(it)
        },
        onClickItem = {
            groupAccountViewModel.onClickContact(it)
        },
        onNext = {
            groupAccountViewModel.makeGroupAccount {
                navController.navigate(Const.GROUP_ACCOUNT_COMPLETED)
            }
        }
    )
}

@Composable
private fun Screen(
    modifier: Modifier = Modifier,
    selectedIdSet: Set<Long>,
    selectedItemList: Array<ContactDto>,
    lazyItemList: LazyPagingItems<ContactDto>,
    onClickSelectedItem: (contactId: Long) -> Unit,
    onClickItem: (contactDto: ContactDto) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (lazyItemList.itemCount == 0) {
            AnimatedLoading()
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        ) {
            items(
                count = selectedItemList.size,
                key = { it },
                itemContent = {
                    val item = selectedItemList[it]
                    SelectedFriendItem(
                        img = item.avatar,
                        name = item.name,
                        onClick = {
                            onClickSelectedItem(item.contactId)
                        }
                    )
                }
            )
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(count = lazyItemList.itemCount) { idx ->
                val item = lazyItemList[idx]
                item?.let {
                    FriendSelectItem(
                        checked = selectedIdSet.contains(item.contactId),
                        img = item.avatar,
                        name = item.name,
                        phone = item.phoneNumber,
                        onClickItem = {
                            onClickItem(it)
                        }
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = selectedIdSet.isNotEmpty(),
            enter = slideInVertically(initialOffsetY = { it / 2 }),
            exit = slideOutVertically(targetOffsetY = { 2 * it })
        ) {
            TextButton(
                onClick = onNext,
                modifier = Modifier
                    .withBottomButton(),
                text = "모두의 통장 생성하기",
                buttonType = ButtonType.ROUNDED
            )
        }
    }
}
