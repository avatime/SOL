@file:Suppress("NAME_SHADOWING")

package com.finance.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.ui.screens.asset.*
import com.finance.android.ui.screens.asset.AssetLifeScreen
import com.finance.android.ui.screens.asset.AssetStockScreen
import com.finance.android.ui.theme.Disabled
import com.finance.android.utils.Const
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HeaderAssetTabBar(
    modifier: Modifier,
    navController: NavController
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val list = listOf("은행", "카드", "라이프", "증권")
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium).value.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                modifier = Modifier.weight(1.0f),
                selectedTabIndex = pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.background,
                indicator = {},
                divider = {},
                edgePadding = 0.dp
            ) {
                list.forEachIndexed { index, text ->
                    Tab(
                        modifier = Modifier
                            .padding(all = 5.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        selected = pagerState.currentPage == index,
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
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
            Spacer(modifier = modifier.width(10.dp))
            IconButton(
                onClick = { navController.navigate(Const.Routes.ADD_ASSET) }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "add"
                )
            }
        }

        val modifier = Modifier
            .fillMaxSize()
            .padding(
                start = dimensionResource(R.dimen.padding_medium),
                end = dimensionResource(R.dimen.padding_medium),
                bottom = dimensionResource(R.dimen.padding_medium)
            )
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10.dp)
            )

        HorizontalPager(
            count = list.size,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) {

            when (it) {
                0 -> {
                    AssetBankScreen(
                        modifier = modifier,
                        navController = navController
                    )
                }
                1 -> {
                    AssetCardScreen(
                        modifier = modifier,
                        navController = navController
                    )
                }
                2 -> {
                    AssetLifeScreen(
                        modifier = modifier,
                        navController = navController
                    )
                }
                3 -> {
                    AssetStockScreen(
                        modifier = modifier,
                        navController = navController
                    )
                }
            }
        }
    }
}
