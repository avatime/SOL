package com.finance.android.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.domain.dto.response.BankInfoResponseDto
import com.finance.android.ui.theme.Disabled
import com.finance.android.utils.Response
import com.finance.android.viewmodels.RemitViewModel

@Composable
fun CpListSheet (modifier: Modifier, remitViewModel: RemitViewModel) {
    var selectedIndex by remember { mutableStateOf(0) }



    val list = listOf("은행", "증권")
    Column(modifier = Modifier.padding(end = 150.dp, bottom = 30.dp)) {
        TabRow(selectedTabIndex = selectedIndex,
            backgroundColor = Color.White,
            modifier = Modifier,
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
                    selectedContentColor = Color.Black,
                    modifier = Modifier.width(80.dp),
                    unselectedContentColor = Disabled,


                    )
            }
        }

        fun launch() {
            remitViewModel.getAllBankData()
        }

        LaunchedEffect(Unit) {
            launch()
        }
        when (selectedIndex) {
            0 -> {
               AllBankList(allBankData = remitViewModel.allBankData)
            }
            1 -> {
                AllStockCpList(allStockCpData = remitViewModel.allStockCpData )

            }

        }

    }

}



@Composable
fun AllBankList(allBankData : MutableState<Response<MutableList<BankInfoResponseDto>>>) {
    Log.i("allbank",allBankData.toString())

    when(val response = allBankData.value) {

        is Response.Failure -> Text(text = "실패")
        is Response.Loading -> Text(text = "로딩중")
        is Response.Success ->
            response.data.forEach {
            it.cpLogo?.let { it1 -> CompanyItem(cpName = it.cpName, cpLogo = it1) }

        }

    }


}

@Composable
fun AllStockCpList(allStockCpData : MutableState<Response<MutableList<BankInfoResponseDto>>>) {

    when(val response = allStockCpData.value) {

        is Response.Failure -> Text(text = "실패")
        is Response.Loading -> Text(text = "로딩중")
        is Response.Success ->
            response.data.forEach {
                it.cpLogo?.let { it1 -> CompanyItem(cpName = it.cpName, cpLogo = it1) }

            }

    }


}

