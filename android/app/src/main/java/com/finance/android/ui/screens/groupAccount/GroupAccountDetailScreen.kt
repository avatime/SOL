package com.finance.android.ui.screens.groupAccount

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.ui.theme.Disabled
import com.finance.android.viewmodels.GroupAccountViewModel

@Composable
fun GroupAccountDetailScreen(
    navController: NavController,
    groupAccountViewModel: GroupAccountViewModel,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = modifier.padding(32.dp)
        ) {
            Column() {
                Text(
                    text = "모두의 통장 이름",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.size(dimensionResource(R.dimen.font_size_small)))
                Text(
                    text = "금액",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
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
                        text = { androidx.compose.material.Text(text = text, fontSize = 18.sp) },
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
                    groupAccountViewModel = groupAccountViewModel,
                    modifier = modifier
                )
            }

            1 -> {
                Log.i("group", "${selectedIndex.value}")
                GroupAccountTradeDetailScreen(
                    navController = navController,
                    groupAccountViewModel = groupAccountViewModel,
                    modifier = modifier
                )
            }
            2 -> {
                Log.i("group", "${selectedIndex.value}")
                GroupAccountMemberScreen(
                    navController = navController,
                    groupAccountViewModel = groupAccountViewModel,
                    modifier = modifier
                )
            }
        }
    }
}



