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
import com.airbnb.lottie.compose.*
import com.finance.android.R
import com.finance.android.ui.screens.AssetBankScreen
import com.finance.android.ui.screens.AssetCardScreen
import com.finance.android.ui.screens.AssetLifeScreen
import com.finance.android.ui.screens.AssetStockScreen
import com.finance.android.ui.theme.Disabled
//import com.google.accompanist.pager.*

@Preview
@Composable
fun HeaderAssetTabBar(
    modifier: Modifier = Modifier
) {

    var selectedIndex by remember { mutableStateOf(0) }


    val list = listOf("은행", "카드", "라이프", "증권")
    Column(modifier = modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedIndex,
            backgroundColor = Color.White,
            modifier = Modifier.padding(end = 20.dp),
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
                    modifier = Modifier.width(10.dp),
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Disabled,

                    )
            }
        }

        when (selectedIndex) {
            0 -> {
                AssetBankScreen()
            }
            1 -> {
                AssetCardScreen()
            }
            2 -> {
                AssetLifeScreen()
            }
            3 -> {
                AssetStockScreen()
            }
        }
    }



}