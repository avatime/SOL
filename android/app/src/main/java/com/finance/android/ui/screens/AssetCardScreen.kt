package com.finance.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R
import com.finance.android.ui.components.CardListItem

@Composable
fun AssetCardScreen(navController : NavController) {
    Column(modifier = Modifier
        .padding(top = dimensionResource(R.dimen.padding_small)))
    {
        AssetCardContainer(modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10)
            ),
            navController = navController
        )
        AssetCardContainer(modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10)
            ),
            navController = navController
        )
    }
}

@Composable
fun AssetCardContainer(modifier: Modifier, navController: NavController) {
    Column(modifier = modifier
        .padding(dimensionResource(R.dimen.padding_medium)))
    {
        CardListItem()
        Text(text = "지금 받을 수 있는 혜택",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.padding_small),
                bottom = dimensionResource(R.dimen.padding_small))
            )
        benefitListItem()
        benefitListItem()
    }
}

@Composable
fun benefitListItem() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(
            top = dimensionResource(R.dimen.padding_small),
            bottom = dimensionResource(R.dimen.padding_small)
        ), verticalAlignment = Alignment.CenterVertically)
    {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://img.lovepik.com/element/40148/6907.png_300.png")
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .padding(end = dimensionResource(R.dimen.padding_medium))
        )
        Text(text = "월납요금(공과금) 10% 할인서비스",
            fontWeight = FontWeight.Bold)
    }
}