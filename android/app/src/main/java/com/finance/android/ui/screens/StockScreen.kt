package com.finance.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R
import com.finance.android.domain.dto.response.FinanceResponseDto
import com.finance.android.ui.components.*
import com.finance.android.utils.Const
import com.finance.android.viewmodels.StockViewModel
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockScreen(
    navController: NavController,
    stockViewModel: StockViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        stockViewModel.launch()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        BaseScreen(
            loading = stockViewModel.loading.value,
            error = stockViewModel.error.value,
            onError = { stockViewModel.launch() },
            calculatedTopPadding = it.calculateTopPadding()
        ) {
            if (stockViewModel.stockList.value.isNotEmpty()) {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    StockContainer(
                        accData = stockViewModel.stockList.value,
                        onClickItem = { data ->
                            navController.navigate("${Const.Routes.STOCK}/${data.fnName}")
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun StockContainer(
    onClickItem: (financeResponseDto: FinanceResponseDto) -> Unit = {},
    accData: Array<FinanceResponseDto> = arrayOf()
) {
    if (accData.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(dimensionResource(id = R.dimen.padding_medium) / 2)
        ) {
            Text(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                text = "주식",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_small)))
            accData.forEach {
                DrawListItem(
                    fnName = it.fnName,
                    fnLogo = it.fnLogo,
                    close = it.close,
                    per = it.per,
                    onClickItem = { onClickItem(it) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun DrawListItem(
    fnName: String = "삼성전자",
    fnLogo: String = "",
    close: Int = 80000,
    per: Double = 10.0,
    onClickItem: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onClickItem()
            }
            .padding(
                vertical = 8.dp,
                horizontal = dimensionResource(id = R.dimen.padding_medium) / 2
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(fnLogo)
                .crossfade(true)
                .build(),
            contentDescription = "기업로고",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(
            modifier = Modifier
                .weight(.05f)
        )
        Text(
            text = fnName
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "${if (per > 0) "+" else ""}$per%",
                color = if (per > 0) Color.Red else Color.Blue,
                fontSize = 14.sp
            )
            Text(
                text = DecimalFormat("#,###원").format(close)
            )
        }
    }
}
