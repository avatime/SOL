package com.finance.android.ui.screens.asset

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.finance.android.domain.dto.response.CardResponseDto
import com.finance.android.ui.components.BaseScreen
import com.finance.android.ui.components.CardListItem_Arrow
import com.finance.android.utils.Const
import com.finance.android.viewmodels.CardViewModel
import java.text.DecimalFormat

@Composable
fun AssetCardScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    cardViewModel: CardViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        cardViewModel.myCardLoad()
    }

    BaseScreen(
        loading = cardViewModel.loading.value,
        error = cardViewModel.error.value,
        onError = { cardViewModel.myCardLoad() },
        calculatedTopPadding = 0.dp
    ) {
        if (cardViewModel.cardList.value != null) {
            AssetCardContainer(
                modifier = modifier,
                navController = navController,
                cardData = cardViewModel.cardList.value!!,
            )
        }
    }
}

@Composable
private fun AssetCardContainer(
    modifier: Modifier,
    navController: NavController,
    cardData: Array<CardResponseDto>,
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        cardData.forEach {
            val pathTmp = Uri.encode(it.cardInfoRes.cardImgPath)
            CardListItem_Arrow(
                cardName = it.cardInfoRes.cardName,
                cardImgPath = it.cardInfoRes.cardImgPath,
                cardFee = "당월 청구 금액 : " + DecimalFormat("#,###원").format(it.cardValueAll),
                onClickItem = {
                    navController.navigate("${Const.Routes.CARD_DETAIL}/${it.cardInfoRes.cardName}/${it.cardInfoRes.cardNumber}/$pathTmp/${it.cardValueAll}/${it.cardInfoRes.cdPdCode}")
                }
            )
        }

        if (cardData.isEmpty()) {
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
