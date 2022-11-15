package com.finance.android.ui.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.ui.screens.remit.AccountScreen
import com.finance.android.ui.screens.remit.ContactScreen
import com.finance.android.ui.screens.remit.RecoScreen
import com.finance.android.ui.theme.Disabled
import com.finance.android.viewmodels.RemitViewModel


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
                    text = { Text(text = text, fontSize = 18.sp, softWrap = false, maxLines = 1) },
                    modifier = Modifier.width(90.dp)
                        .clip(RoundedCornerShape(10.dp)),
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
                AccountScreen(
                    remitViewModel = remitViewModel, navController = navController
                )
            }
            2 -> {
                ContactScreen(remitViewModel = remitViewModel, navController = navController)
            }
        }
    }

}









