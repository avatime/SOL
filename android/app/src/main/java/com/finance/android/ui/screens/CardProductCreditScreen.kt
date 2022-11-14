package com.finance.android.ui.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R
import com.finance.android.domain.dto.response.CardRecommendInfoResponseDto
import com.finance.android.ui.components.CardListItem_Arrow
import com.finance.android.utils.Const

@Composable
fun CardProductCreditScreen(
    navController: NavController,
    creditCardList: MutableList<CardRecommendInfoResponseDto>
) {
    creditCardList.forEach {
        CardProductContainer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(10)
                ),
            navController = navController,
            cardData = it
        )
    }
}

@Composable
private fun CardProductContainer(
    modifier: Modifier,
    navController: NavController,
    cardData: CardRecommendInfoResponseDto
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium))
    )
    {
        val pathTmp = Uri.encode(cardData.cardImage)
        CardListItem_Arrow(
            cardName = cardData.cardName,
            cardImgPath = cardData.cardImage,
            onClickItem = {
                navController.navigate("${Const.Routes.CARD_BENEFIT}/${cardData.cardPdCode}/${pathTmp}/${cardData.cardName}")
            }
        )
        Text(
            text = "지금 받을 수 있는 혜택",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(
                    top = dimensionResource(R.dimen.padding_small),
                    bottom = dimensionResource(R.dimen.padding_small)
                )
        )
        cardData.cardBenefitInfoList.forEach { benefit ->
            BenefitListItem(
                benefitSummary = benefit.cardBenefitSummary,
                companyLogoPath = benefit.cardBenefitImage
            )
        }
    }
}


@Composable
private fun BenefitListItem(
    benefitSummary: String?,
    companyLogoPath: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(companyLogoPath)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .padding(end = dimensionResource(R.dimen.padding_medium)),
            colorFilter = ColorFilter.tint(colorResource(R.color.noActiveColor))
        )

        Text(
            text = "$benefitSummary",
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}