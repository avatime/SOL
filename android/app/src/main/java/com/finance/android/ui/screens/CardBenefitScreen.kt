package com.finance.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R
import com.finance.android.domain.dto.response.CardBenefitDetailResponseDto
import com.finance.android.ui.components.*
import com.finance.android.utils.Response
import com.finance.android.viewmodels.CardViewModel

@Composable

fun CardBenefitScreen(
    onClose: () -> Unit,
    cardViewModel: CardViewModel = hiltViewModel(),
    cardProductCode: Int,
    cdImgPath: String,
    cdName: String,
) {

    fun launch() {
        cardViewModel.loadCardBenefit(cardProductCode)
    }

    LaunchedEffect(Unit) {
        launch()
    }

    Scaffold(topBar = {
        BackHeaderBar(text = "카드", modifier = Modifier, onClickBack = onClose)
    }) { innerPaddingModifier ->
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(top = innerPaddingModifier.calculateTopPadding())
        ) {
            when (cardViewModel.getLoadCardBenefit()) {
                is Response.Success -> {
                    val changeSc = remember { mutableStateOf(false) }
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
                        benefitList.forEach {
                            BenefitSummaryListItem(
                                benefitSummary = it.cardBenefitSummary,
                                benefitName = it.cardBenefitName,
                                companyLogoPath = it.cardBenefitImage
                            )
                        }
                        TextButton(onClick = {
                            changeSc.value = true
                        },
                            text = "혜택 더보기",
                            buttonColor = ButtonColor.WHITE,
                            buttonType = ButtonType.ROUNDED,
                            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium))
                        )
                        if (changeSc.value) {
                            DialogWithOneButton(
                                modifier = Modifier
                                    .defaultMinSize(minWidth = 350.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(color = MaterialTheme.colorScheme.surface)
                                    .padding(dimensionResource(R.dimen.padding_medium))
                                    .fillMaxSize(),
                                onClick = { changeSc.value = false },
                                buttonText = "닫기",
                                benefitList = (cardViewModel.cardBenefitDetail.value as Response.Success).data
                            )
                        }
                    }
                }

                is Response.Loading -> {}
                else -> {}
            }

        }
    }

}

@Composable
private fun DialogWithOneButton(
    modifier: Modifier,
    title: String = "카드 혜택",
    onClick: () -> Unit,
    buttonText: String,
    benefitList: MutableList<CardBenefitDetailResponseDto>
) {
    Dialog(onDismissRequest = { onClick }) {
        Column(
            modifier = modifier,
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                fontSize = dimensionResource(id = R.dimen.font_size_large).value.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.padding_small))
            )
            Spacer(modifier = Modifier.height(5.dp))
            Column(modifier = Modifier
                .weight(1.0f)
                .verticalScroll(rememberScrollState())) {
                benefitList.forEach {
                    Column(modifier = Modifier.padding(vertical = 10.dp)) {
                        Row(modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
                        verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current).data(it.cardBenefitImage)
                                    .crossfade(true).build(),
                                contentDescription = "카드 사진",
                                modifier = Modifier
                                    .size(48.dp))
                            Text(text = it.cardBenefitName, fontSize = 20.sp, fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
                        }
                        Text(text = it.cardBenefitDetail, color = Color.Gray)
                    }
                }
            }
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(R.dimen.padding_medium)),
                onClick = onClick,
                text = buttonText,
                buttonType = ButtonType.ROUNDED
            )
        }
    }
}

@Composable
private fun BenefitSummaryListItem(
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
                .data(companyLogoPath)
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
            Text(text = benefitName,
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