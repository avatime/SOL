package com.finance.android.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
    Column(modifier = Modifier
        .height(450.dp)) {
        TabRow(selectedTabIndex = selectedIndex,
            backgroundColor = Color.White,
            modifier = Modifier.padding(end = 200.dp, bottom = 30.dp,top=10.dp),
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
                    modifier = Modifier.width(30.dp),
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Disabled,
                    )
            }
        }

        fun launch() {
            remitViewModel.getAllBankData()
            remitViewModel.getAllStockCpData()
        }

        LaunchedEffect(Unit) {
            launch()
        }
        when (selectedIndex) {

            0 -> {
                Column(modifier = modifier.fillMaxSize()
                        ){
                    AllBankList(allBankData = remitViewModel.allBankData,remitViewModel = remitViewModel)
                }



            }
            1 -> {
                Column(modifier = modifier.fillMaxSize()) {
                    AllStockCpList(allStockCpData = remitViewModel.allStockCpData, remitViewModel=remitViewModel)
                }


            }

        }

    }

}



@Composable
fun AllBankList(allBankData : MutableState<Response<MutableList<BankInfoResponseDto>>> ,remitViewModel: RemitViewModel) {
    Log.i("allbank",allBankData.toString())

    when(val response = allBankData.value) {

        is Response.Failure -> Text(text = "실패")
        is Response.Loading -> Text(text = "로딩중")
        is Response.Success ->
          LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier
              .width(400.dp)
              .background(Color.White)) {
              items(response.data) { idx ->
                  CompanyItem(cpCode = idx.cpCode, cpName = idx.cpName, cpLogo = idx.cpLogo , remitViewModel = remitViewModel)

              }
          }

    }


}

@Composable
fun AllStockCpList(allStockCpData : MutableState<Response<MutableList<BankInfoResponseDto>>>,remitViewModel: RemitViewModel) {

    when(val response = allStockCpData.value) {

        is Response.Failure -> Text(text = "실패")
        is Response.Loading -> Text(text = "로딩중")
        is Response.Success ->
            LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier
                .height(400.dp)
                .background(Color.White)) {
                items(response.data) { idx ->
                    CompanyItem(cpCode= idx.cpCode,
                        cpName = idx.cpName, cpLogo = idx.cpLogo , remitViewModel = remitViewModel)

                }
            }

    }


}

