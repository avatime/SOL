package com.finance.android.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.finance.android.R
import com.finance.android.ui.screens.AssetBankScreen
import com.finance.android.ui.screens.AssetCardScreen
import com.finance.android.ui.screens.AssetLifeScreen
import com.finance.android.ui.screens.AssetStockScreen
import com.finance.android.ui.theme.Disabled
//import com.google.accompanist.pager.*

@Composable
fun HeaderAssetTabBar(
    modifier: Modifier,
    navController: NavController
) {

    var selectedIndex by remember { mutableStateOf(0) }


    val list = listOf("은행", "카드", "라이프", "증권")
    Column(modifier = modifier.fillMaxSize()) {
        TabRow(

            selectedTabIndex = selectedIndex,
            backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .padding(end = 0.dp),
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
                    text = { Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.Bold ) },
                    modifier = Modifier.width(20.dp),
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Disabled,

                    )
            }
        }

        when (selectedIndex) {
            0 -> {
                AssetBankScreen(navController)
            }
            1 -> {
                AssetCardScreen()
            }
            2 -> {
                AssetLifeScreen(navController)
            }
            3 -> {
                AssetStockScreen(navController)
            }
        }
    }



}