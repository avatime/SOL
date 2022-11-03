package com.finance.android.ui.components


import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.finance.android.R
import com.finance.android.ui.screens.AccountScreen
import com.finance.android.ui.screens.ContactScreen
import com.finance.android.ui.screens.RecoScreen
import com.finance.android.ui.theme.Disabled
import com.finance.android.viewmodels.RemitViewModel
import com.google.accompanist.pager.*


@Composable
fun HeaderRemitTabBar(
    modifier: Modifier = Modifier,
    remitViewModel: RemitViewModel,
    navController: NavController
) {


    var selectedIndex by remember { mutableStateOf(0) }


    val list = listOf("추천", "계좌", "연락처")
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
                    text = { Text(text = text, fontSize = 18.sp,) },
                    modifier = Modifier.width(80.dp),
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Disabled,


                    )
            }
        }

        when (selectedIndex) {
            0 -> {
                RecoScreen(
                    remitViewModel = remitViewModel,
                    navController = navController
                )
            }
            1 -> {
                AccountScreen(remitViewModel = remitViewModel,                    navController = navController
                )
            }
            2 -> {
                ContactScreen()
            }
        }
    }



}









