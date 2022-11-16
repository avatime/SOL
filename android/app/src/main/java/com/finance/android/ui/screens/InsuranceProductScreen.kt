package com.finance.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R
import com.finance.android.domain.dto.response.InsuranceProductInfoResponseDto
import com.finance.android.utils.Const

@Composable
fun InsuranceProductScreen(
    navController: NavController,
    insuranceProductList: Array<InsuranceProductInfoResponseDto>
) {
    LazyColumn(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
    ) {
        items(count = insuranceProductList.size) {
            val data = insuranceProductList[it]
            InsuranceProductItem(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(10)
                    ),
                data = data,
                onClick = {
                    navController.navigate("${Const.Routes.INSURANCE}/${data.id}/${data.name}")
                }
            )
        }
    }
}

@Composable
private fun InsuranceProductItem(
    modifier: Modifier,
    onClick: () -> Unit,
    data: InsuranceProductInfoResponseDto
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10))
            .clickable {
                onClick()
            }
            .padding(dimensionResource(R.dimen.padding_medium)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png")
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp).weight(1f)
        ) {
            Text(text = data.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = data.type, fontSize = 12.sp, color = Color(R.color.noActiveColor))
        }
    }
}
