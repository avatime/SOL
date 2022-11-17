package com.finance.android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.ui.screens.remit.AccountScreen
import com.finance.android.ui.screens.remit.ContactScreen
import com.finance.android.ui.screens.remit.RecoScreen
import com.finance.android.ui.theme.Disabled
import com.finance.android.viewmodels.RemitViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HeaderRemitTabBar(
    modifier: Modifier = Modifier,
    remitViewModel: RemitViewModel,
    navController: NavController
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val expanded = remember { mutableStateOf(false) }
    val list = listOf("추천", "계좌", "연락처")
    Column(modifier = modifier.fillMaxSize()) {
        try {
            Class
                .forName("androidx.compose.material3.TabRowKt")
                .getDeclaredField("ScrollableTabRowMinimumTabWidth")
                .apply {
                    isAccessible = true
                }
                .set(this, 0f)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        ScrollableTabRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = com.finance.android.R.dimen.padding_medium).value.dp),
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.surface,
            indicator = {},
            divider = {},
            edgePadding = 0.dp
        ) {
            list.forEachIndexed { index, text ->
                Tab(
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                            remitViewModel.requestRemit.value = index == 2
                        }
                    },
                    text = {
                        Text(
                            text = text,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    selectedContentColor = MaterialTheme.colorScheme.onSurface,
                    unselectedContentColor = Disabled
                )
            }
        }

        HorizontalPager(
            count = list.size,
            state = pagerState
        ) {
            when (it) {
                0 -> {
                    RecoScreen(
                        remitViewModel = remitViewModel,
                        navController = navController,
                        expanded = expanded
                    )
                }
                1 -> {
                    AccountScreen(
                        remitViewModel = remitViewModel,
                        navController = navController
                    )
                }
                2 -> {
                    ContactScreen(remitViewModel = remitViewModel, navController = navController)
                }
            }
        }
    }
}
