package com.finance.android.ui.screens

import android.net.Uri
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R
import com.finance.android.domain.dto.response.CardResponseDto
import com.finance.android.ui.components.CardListItem
import com.finance.android.ui.components.CardListItem_Arrow
import com.finance.android.utils.Const
import com.finance.android.viewmodels.CardViewModel
import com.finance.android.utils.Response
import java.text.DecimalFormat

@Composable
fun AssetCardScreen(
    navController: NavController,
    cardViewModel: CardViewModel = hiltViewModel()
) {
    fun launch() {
        cardViewModel.myCardLoad()
    }

    LaunchedEffect(Unit) {
        launch()
    }

    Column()
    {
        when (val data = cardViewModel.getLoadState()) {
            is Response.Success -> {
                    AssetCardContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.padding_medium))
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(10)
                            ),
                        navController = navController,
                        cardData = (cardViewModel.cardList.value as Response.Success).data
                    )
            }
            is Response.Loading -> {}
            else -> {}
        }
    }
}

@Composable
private fun AssetCardContainer(
    modifier: Modifier,
    navController: NavController,
    cardData: MutableList<CardResponseDto>
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium))
    )
    {
        cardData!!.forEach {
            val pathTmp = Uri.encode(it.cardInfoRes.cardImgPath)
            CardListItem_Arrow(
                cardName = it.cardInfoRes.cardName,
                cardImgPath = it.cardInfoRes.cardImgPath,
                cardFee = "당월 청구 금액 : "+ DecimalFormat("#,###원").format(it.cardValueAll),
                onClickItem = {
                    navController.navigate("${Const.Routes.CARD_DETAIL}/${it.cardInfoRes.cardNumber}/${pathTmp}/${it.cardInfoRes.cardName}")
                }
            )
        }

        if (cardData.size == 0) {
            Text(text = "등록된 자산이 없어요.", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
//        Text(
//            text = "지금 받을 수 있는 혜택",
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .padding(
//                    top = dimensionResource(R.dimen.padding_small),
//                    bottom = dimensionResource(R.dimen.padding_small)
//                )
//        )
//        cardData.cardBenefitInfoList!!.forEach { benefit ->
//            benefitListItem(
//                benefitSummary = benefit.cardBenefitSummary,
//                companyLogoPath = benefit.cardBenefitImage
//            )
//        }
    }
}

//@Composable
//private fun benefitListItem(
//    benefitSummary: String?,
//    companyLogoPath: String
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically
//    )
//    {
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data("$companyLogoPath")
//                .crossfade(true)
//                .build(),
//            contentDescription = null,
//            modifier = Modifier
//                .size(45.dp)
//                .padding(end = dimensionResource(R.dimen.padding_medium)),
//            colorFilter = ColorFilter.tint(colorResource(R.color.noActiveColor))
//        )
//
//        Text(
//            text = "$benefitSummary",
//            fontWeight = FontWeight.Bold,
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis
//        )
//    }
//}