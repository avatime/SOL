package com.finance.android.ui.screens.groupAccount

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.ui.components.AnimatedLoading
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.ui.theme.Disabled
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.viewmodels.GroupAccountViewModel

@Composable
fun GroupAccountDetailScreen(
    navController: NavController,
    groupAccountViewModel: GroupAccountViewModel,
    modifier: Modifier,
) {

    fun launch() {
        groupAccountViewModel.postGroupAccountInfo(groupAccountViewModel.paId.value)
    }

    LaunchedEffect(Unit) {
        launch()
    }

    val isAvalibale = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        when (val response = groupAccountViewModel.groupAccountInfo.value) {
            is Response.Failure -> {
                androidx.compose.material.Text(text = "실패")
            }
            is Response.Loading -> AnimatedLoading(text = "")
            is Response.Success -> {
                Log.i("group", "직책 ${response.data.type}")
                if (response.data.type == "관리자") {
                    isAvalibale.value = true
                }
                Text(
                    text = response.data.paName,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 25.dp)
                )
                Spacer(modifier = Modifier.size(dimensionResource(R.dimen.font_size_small)))
                Text(
                    text = response.data.amount.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 25.dp)
                )
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,

            ) {

            Spacer(modifier = Modifier.weight(0.1f))
            TextButton(
                onClick = {
                    navController.navigate(Const.GROUP_ACCOUNT_INPUT_MONEY_SCREEN)
                    groupAccountViewModel.screenType.value = 2
                },
                text = "       입   금       ",
                buttonType = ButtonType.ROUNDED
            )
            Spacer(modifier = Modifier.weight(0.1f))
            TextButton(
                onClick = {
                    navController.navigate(Const.GROUP_ACCOUNT_INPUT_MONEY_SCREEN)
                    groupAccountViewModel.screenType.value = 3
                },
                text = "      출   금       ",
                buttonType = ButtonType.ROUNDED,
                enabled = isAvalibale.value
            )
            Spacer(modifier = Modifier.weight(0.1f))

        }//end of Row

            val list = listOf("회비", "입출금", "친구")
            val selectedIndex = remember { mutableStateOf(0) }
            Column(modifier = modifier.fillMaxWidth()) {
                TabRow(selectedTabIndex = selectedIndex.value,
                    backgroundColor = Color.White,
                    modifier = Modifier.padding(end = 150.dp),
                    indicator = {
                        TabRowDefaults.Indicator(
                            modifier = Modifier
                                .tabIndicatorOffset(it[selectedIndex.value]),
                            color = Color.Transparent,
                            height = TabRowDefaults.IndicatorHeight * 1.5F
                        )
                    },
                    divider = {
                        TabRowDefaults.Divider(
                            color = Color.Transparent
                        )
                    }

                ) {

                    list.forEachIndexed { index, text ->
                        val selected = selectedIndex.value == index
                        Tab(
                            selected = selected,
                            onClick = { selectedIndex.value = index },
                            text = {
                                androidx.compose.material.Text(
                                    text = text,
                                    fontSize = 16.sp,
                                    softWrap = false,
                                    maxLines = 1
                                )
                            },
                            modifier = Modifier.width(80.dp),
                            selectedContentColor = Color.Black,
                            unselectedContentColor = Disabled,
                        )
                    }
                }

            }
            when (selectedIndex.value) {
                0 -> {
                    Log.i("group", "${selectedIndex.value}")
                    GroupAccountDuesScreen(
                        navController = navController,
                        groupAccountViewModel = groupAccountViewModel
                    )
                }

                1 -> {
                    Log.i("group", "${selectedIndex.value}")
                    GroupAccountTradeDetailScreen(
                        groupAccountViewModel = groupAccountViewModel,
                        modifier = modifier
                    )
                }
                2 -> {
                    Log.i("group", "${selectedIndex.value}")
                    GroupAccountMemberScreen(
                        groupAccountViewModel = groupAccountViewModel
                    )
                }
                

        }

    }
}





