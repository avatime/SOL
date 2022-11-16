package com.finance.android.ui.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.R
import com.finance.android.domain.dto.response.BankInfoResponseDto
import com.finance.android.ui.theme.Disabled
import com.finance.android.utils.Response
import com.finance.android.viewmodels.RemitViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CpListSheet(modifier: Modifier, remitViewModel: RemitViewModel, onDismiss: () -> Unit) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val list = listOf("은행", "증권")

    Column {
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
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium).value.dp),
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

        HorizontalPager(
            count = list.size,
            state = pagerState
        ) {
            when (it) {
                0 -> {
                    Column(
                        modifier = modifier.fillMaxSize()
                    ) {
                        AllBankList(
                            allBankData = remitViewModel.allBankData,
                            remitViewModel = remitViewModel,
                            onClickItem = onDismiss
                        )
                    }
                }
                1 -> {
                    Column(modifier = modifier.fillMaxSize()) {
                        AllStockCpList(
                            allStockCpData = remitViewModel.allStockCpData,
                            remitViewModel = remitViewModel,
                            onClickItem = onDismiss
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AllBankList(
    allBankData: MutableState<Response<MutableList<BankInfoResponseDto>>>,
    remitViewModel: RemitViewModel,
    onClickItem: () -> Unit
) {
    Log.i("allbank", allBankData.toString())

    when (val response = allBankData.value) {
        is Response.Failure -> Text(text = "실패")
        is Response.Loading -> Text(text = "로딩중")
        is Response.Success ->
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(response.data) { idx ->
                    CompanyItem(
                        cpCode = idx.cpCode,
                        cpName = idx.cpName,
                        cpLogo = idx.cpLogo,
                        remitViewModel = remitViewModel,
                        onClick = onClickItem
                    )
                }
            }
    }
}

@Composable
fun AllStockCpList(
    allStockCpData: MutableState<Response<MutableList<BankInfoResponseDto>>>,
    remitViewModel: RemitViewModel,
    onClickItem: () -> Unit
) {
    when (val response = allStockCpData.value) {
        is Response.Failure -> Text(text = "실패")
        is Response.Loading -> Text(text = "로딩중")
        is Response.Success ->
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(response.data) { idx ->
                    CompanyItem(
                        cpCode = idx.cpCode,
                        cpName = idx.cpName,
                        cpLogo = idx.cpLogo,
                        remitViewModel = remitViewModel,
                        onClick = onClickItem
                    )
                }
            }
    }
}
