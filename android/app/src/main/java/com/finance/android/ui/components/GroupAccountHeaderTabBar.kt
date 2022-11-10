package com.finance.android.ui.components
import androidx.compose.foundation.layout.*
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.finance.android.R
import com.finance.android.ui.screens.groupAccount.GroupAccountDuesScreen
import com.finance.android.ui.screens.groupAccount.GroupAccountMemberScreen
import com.finance.android.ui.screens.groupAccount.GroupAccountTradeDetailScreen

import com.finance.android.ui.theme.Disabled
import com.finance.android.utils.Const
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.GroupAccountViewModel


@Composable
fun GroupAccountHeaderTabBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    groupAccountViewModel: GroupAccountViewModel
) {
    var selectedIndex by remember { mutableStateOf(0) }
    val innerNavController = rememberNavController()

    val list = listOf("회비", "입출금", "친구")
    Column(modifier = modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedIndex,
            backgroundColor = Color.White,
            modifier = Modifier.padding(end = 150.dp),
            indicator = {
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .tabIndicatorOffset(it[selectedIndex]),
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
                val selected = selectedIndex == index
                Tab(
                    selected = selected,
                    onClick = { selectedIndex = index },
                    text = { Text(text = text, fontSize = 18.sp) },
                    modifier = Modifier.width(80.dp),
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Disabled,
                    )
                }

            when (selectedIndex){
                0 -> GroupAccountDuesScreen(
                    navController = innerNavController,
                    groupAccountViewModel = groupAccountViewModel,
                    modifier = modifier
                )
                1 -> GroupAccountTradeDetailScreen(
                    navController = innerNavController,
                    groupAccountViewModel = groupAccountViewModel,
                    modifier = modifier
                )
                2 -> GroupAccountMemberScreen(
                    navController = innerNavController,
                    groupAccountViewModel = groupAccountViewModel,
                    modifier = modifier
                )
            }

            }
        }


    }












