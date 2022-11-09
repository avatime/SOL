package com.finance.android.ui.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R
import com.finance.android.domain.dto.response.CardBenefitInfoResponseDto
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.utils.Response
import com.finance.android.viewmodels.CardViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable

fun CardDetailFragment(
    navController: NavController,
    onClose: () -> Unit,
    cardViewModel: CardViewModel = hiltViewModel(),
    cdNo: String,
    cdImgPath: String,
    cdName: String,
) {
    val current = LocalDateTime.now()
    val formattedM = current.format(DateTimeFormatter.ofPattern("M"))
    val formattedY = current.format(DateTimeFormatter.ofPattern(("YYYY")))
    val month = formattedM.toInt()
    val year = formattedY.toInt()

    fun launch() {
        cardViewModel.loadCardBill(cdNo, year, month)
        cardViewModel.loadCardBenefit(cdNo)
    }

    LaunchedEffect(Unit) {
        launch()
    }

    Scaffold(topBar = {
        BackHeaderBar(text = "내 카드", modifier = Modifier, onClickBack = onClose)
    }) { innerPaddingModifier ->
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(top = innerPaddingModifier.calculateTopPadding())
        ) {
            when (val data = cardViewModel.getLoadCardBillandBenefit()) {
                is Response.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                start = dimensionResource(R.dimen.padding_medium),
                                end = dimensionResource(R.dimen.padding_medium)
                            )
                            .background(color = MaterialTheme.colorScheme.background),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val benefitList = (cardViewModel.cardBenefit.value as Response.Success).data
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current).data(cdImgPath)
                                .crossfade(true).build(),
                            contentDescription = "카드 사진",
                            modifier = Modifier
                                .size(250.dp)
                                .padding(
                                    top = dimensionResource(R.dimen.padding_small),
                                    bottom = dimensionResource(R.dimen.padding_medium)
                                )

                        )
                        Text(
                            text = cdName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center,
                        )

                        Text(text = "Benefit",
                            fontSize = 30.sp, color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_large),
                                bottom = 4.dp)
                        )
                        Text(text = "주요 혜택", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Divider(modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium),
                            bottom = dimensionResource(R.dimen.padding_small)))
                        benefitList!!.forEach {
                            benefitSummaryListItem(
                                benefitSummary = it.cardBenefitSummary,
                                benefitName = it.cardBenefitName,
                                companyLogoPath = it.cardBenefitImage
                            )
                        }
                    }
                }

                is Response.Loading -> {
                    Text(text = "로딩중")
                }
                else -> {
                    Text(text = "실패")
                }
            }

        }
    }

}

@Composable
private fun benefitSummaryListItem(
    benefitSummary: String?,
    benefitName: String,
    companyLogoPath: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("$companyLogoPath")
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .padding(end = dimensionResource(R.dimen.padding_medium)),
            colorFilter = ColorFilter.tint(colorResource(R.color.noActiveColor))
        )
        Column(modifier = Modifier
            .padding(end = dimensionResource(R.dimen.padding_medium))
        ) {
            Text(text = "$benefitName",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
            Text(
                text = "$benefitSummary",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}