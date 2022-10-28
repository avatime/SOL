package com.finance.android.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.finance.android.ui.screens.AccountScreen
import com.finance.android.ui.screens.ContactScreen
import com.finance.android.ui.screens.RecoScreen
import com.finance.android.ui.theme.Disabled
import com.google.accompanist.pager.*


val PAGES = listOf("추천", "계좌", "연락처")

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HeaderRemitTabBar() {


    var selectedIndex by remember { mutableStateOf(0) }



    val list = listOf("추천", "계좌", "연락처")
    Row(modifier = Modifier.padding(start =80.dp, end = 80.dp, bottom = 30.dp)) {
        TabRow(selectedTabIndex = selectedIndex,
            backgroundColor = Color.White,
            modifier = Modifier,


        ) {
            list.forEachIndexed { index, text ->
                val selected = selectedIndex == index
                Tab(

                    selected = selected,
                    onClick = { selectedIndex = index },
                    text = { Text(text = text) },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Disabled,


                    )
            }
        }




    }
    when(selectedIndex) {
        0 -> {
            RecoScreen()
        }
        1 -> {
            AccountScreen()
        }
        2 -> {
            ContactScreen()
        }
    }





}









