package com.finance.android.ui.screens.asset

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.domain.dto.response.InsuranceInfoResponseDto
import com.finance.android.ui.components.InsuranceListItem_Normal
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.viewmodels.InsuranceViewModel
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun AssetLifeScreen(
    navController: NavController,
    insuranceViewModel: InsuranceViewModel = hiltViewModel()
) {
    fun launch() {
        insuranceViewModel.myIsLoad()
    }

    LaunchedEffect(Unit) {
        launch()
    }

    when (insuranceViewModel.getLoadState()) {
        is Response.Success -> {
            val isData = (insuranceViewModel.isList.value as Response.Success).data
            Column()
            {
                AssetLifeContainer(modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(10.dp)
                    ),
                    isList = isData.list,
                    navController = navController
                )
                if(isData.totalFee != 0) {
                    AssetLifeContainer2(modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(10.dp)
                        ),
                        value = isData.totalFee)
                }
            }
        }
        is Response.Loading -> {}
        else -> {}
    }
}

@Composable
private fun AssetLifeContainer(
    modifier: Modifier,
    isList: MutableList<InsuranceInfoResponseDto>,
    navController: NavController
    ) {
    Column(modifier = modifier
        .padding(dimensionResource(R.dimen.padding_medium)))
    {
        isList.forEach {
            InsuranceListItem_Normal(
                insuranceName = it.isPdName,
                fee = it.isPdFee,
                myName = it.name,
                isName = it.isName,
                onClickItem = {
                    navController.navigate("${Const.Routes.INSURANCE}/${it.isPdId}/${it.isPdName}")
                }
            )
        }
        if(isList.size == 0) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "등록된 자산이 없어요.",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 80.dp)
                )
            }
        }
    }
}

@Composable
private fun AssetLifeContainer2(
    modifier: Modifier,
    value: Int
) {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("M월")
    val formatted = current.format(formatter)
    Column(modifier = modifier
        .padding(dimensionResource(R.dimen.padding_medium)))
    {
        Row (verticalAlignment = Alignment.CenterVertically)
        {
            Text(text = "신한라이프",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(end = 16.dp),
            )
            Text(text = "$formatted 납입보험료", fontSize = 12.sp)
        }
        Text(text = DecimalFormat("#,###원").format(value)?:"0원",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
        modifier = Modifier.padding(top = 24.dp, bottom = 24.dp, start = 8.dp))
    }
}