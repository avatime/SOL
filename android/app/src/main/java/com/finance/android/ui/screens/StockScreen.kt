package com.finance.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R
import com.finance.android.domain.dto.response.FinanceResponseDto
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.viewmodels.FinanceViewModel
import java.text.DecimalFormat

@Composable
fun StockScreen(
    navController: NavController,
    financeViewModel: FinanceViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        financeViewModel.Load()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        when (val data = financeViewModel.getLoadState()) {
            is Response.Success -> {
                StockContainer(
                    navController = navController,
                    accData = (financeViewModel.financeList.value as Response.Success).data
                )
            }
            is Response.Loading -> {}
            else -> {}
        }
    }
}


@Composable
fun StockContainer(
    navController: NavController,
    accData: Array<FinanceResponseDto>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10)
            )
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        accData.forEach {
            DrawListItem(
                fnName = it.fnName,
                fnLogo = it.fnLogo,
                close = it.close,
                per = it.per,
                onClickItem = {
                    navController.navigate("${Const.Routes.STOCK}/${it.fnName}/${it.close}/${it.per}")
                }
            )
        }
    }
}

@Composable
fun DrawListItem(
    fnName: String,
    fnLogo: String,
    close: Int,
    per: Double,
    onClickItem: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                       onClickItem()
            },
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
                .clip(CircleShape),
        )
        Spacer(
            modifier = Modifier
                .weight(.05f)
        )
        Text(
            text = fnName,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "$per%",
                color = if (per > 0) Color(0xFFFF0000) else Color(0xFF3F51B5),
                fontSize = 19.sp
            )
            Text(
                text = DecimalFormat("#,###원").format(close),
                fontSize = 15.sp
            )
        }
    }
}