package com.finance.android.ui.screens.addasset

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.*
import com.finance.android.R
import com.finance.android.domain.dto.response.BankAccountResponseDto
import com.finance.android.domain.dto.response.CardInfoResponseDto
import com.finance.android.domain.dto.response.InsuranceInfoResponseDto
import com.finance.android.ui.components.*
import com.finance.android.utils.Response
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.AddAssetViewModel

@Composable
fun AddAssetResultScreen(
    modifier: Modifier = Modifier,
    addAssetViewModel: AddAssetViewModel,
    onClickNext: () -> Unit
) {
    when (addAssetViewModel.getRegisterState()) {
        is Response.Success -> DrawScreen(
            modifier = modifier,
            accountList = (addAssetViewModel.accountList.value as Response.Success).data
                .filterIndexed { idx, _ -> addAssetViewModel.accountCheckList[idx].value },
            cardList = (addAssetViewModel.cardList.value as Response.Success).data
                .filterIndexed { idx, _ -> addAssetViewModel.cardCheckList[idx].value },
            stockAccountList = (addAssetViewModel.stockAccountList.value as Response.Success).data
                .filterIndexed { idx, _ -> addAssetViewModel.stockAccountCheckList[idx].value },
            insuranceList = (addAssetViewModel.insuranceList.value as Response.Success).data
                .filterIndexed { idx, _ -> addAssetViewModel.insuranceCheckList[idx].value },
            onClickNext = onClickNext
        )
        else -> DrawLoading()
    }
}

@Composable
private fun DrawLoading() {
    AnimatedLoading(text = stringResource(id = R.string.msg_wait_register_asset))
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun DrawScreen(
    modifier: Modifier,
    accountList: List<BankAccountResponseDto>,
    cardList: List<CardInfoResponseDto>,
    stockAccountList: List<BankAccountResponseDto>,
    insuranceList: List<InsuranceInfoResponseDto>,
    onClickNext: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ic_done))
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever,
        )
        val dynamicProperties = rememberLottieDynamicProperties(
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = MaterialTheme.colorScheme.primary.toArgb(),
                keyPath = arrayOf(
                    "**"
                )
            ),
            rememberLottieDynamicProperty(
                property = LottieProperty.STROKE_COLOR,
                value = MaterialTheme.colorScheme.primary.toArgb(),
                keyPath = arrayOf(
                    "**"
                )
            )
        )

        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            LottieAnimation(
                composition,
                progress = { progress },
                modifier = Modifier.size(160.dp),
                dynamicProperties = dynamicProperties
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(
                    id = R.string.msg_register_asset,
                    accountList.size + cardList.size + stockAccountList.size
                ),
                style = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.font_size_large).value.sp,
                    lineHeight = dimensionResource(id = R.dimen.font_size_large).value.sp
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.weight(1.0f)) {
                LazyColumn {
                    if (accountList.isNotEmpty()) {
                        stickyHeader {
                            StickyHeader(stringResource(id = R.string.btn_tab_account))
                        }
                    }
                    items(
                        count = accountList.size,
                        itemContent = { idx ->
                            val item = accountList[idx]
                            AccountListItem_Normal(
                                contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.padding_medium)),
                                accountNumber = item.acNo,
                                balance = item.balance,
                                accountName = item.acName,
                                companyLogoPath = item.cpLogo,
                                acMain = item.acMain
                            )
                        }
                    )
                    if (cardList.isNotEmpty()) {
                        stickyHeader {
                            StickyHeader(stringResource(id = R.string.btn_tab_card))
                        }
                    }
                    items(
                        count = cardList.size,
                        itemContent = { idx ->
                            val item = cardList[idx]
                            CardListItem_Normal(
                                contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.padding_medium)),
                                cardName = item.cardName,
                                cardImgPath = item.cardImgPath
                            )
                        }
                    )
                    if (stockAccountList.isNotEmpty()) {
                        stickyHeader {
                            StickyHeader(stringResource(id = R.string.btn_tab_stock))
                        }
                    }
                    items(
                        count = stockAccountList.size,
                        itemContent = { idx ->
                            val item = stockAccountList[idx]
                            AccountListItem_Normal(
                                contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.padding_medium)),
                                accountNumber = item.acNo,
                                balance = item.balance,
                                accountName = item.acName,
                                companyLogoPath = item.cpLogo,
                                acMain = item.acMain
                            )
                        }
                    )
                    if (insuranceList.isNotEmpty()) {
                        stickyHeader {
                            StickyHeader(stringResource(id = R.string.btn_tab_insurance))
                        }
                    }
                    items(
                        count = insuranceList.size,
                        itemContent = { idx ->
                            val item = insuranceList[idx]
                            InsuranceListItem_Normal(
                                insuranceName = item.isPdName,
                                fee = item.isPdFee,
                                myName = item.name,
                                isName = item.isName,
                                onClickItem = {}
                            )
                        }
                    )
                }
            }
            TextButton(
                onClick = { onClickNext() },
                text = stringResource(id = R.string.btn_confirm),
                buttonType = ButtonType.ROUNDED,
                modifier = Modifier.withBottomButton()
            )
        }
    }
}

@Composable
private fun StickyHeader(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_medium).value.dp,
                vertical = 5.dp
            )
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp
        )
    }
}

@Preview
@Composable
private fun PreviewLoading() {
    DrawLoading()
}

@Preview
@Composable
private fun PreviewScreen() {
    DrawScreen(
        modifier = Modifier,
        accountList = listOf(),
        cardList = listOf(),
        stockAccountList = listOf(),
        insuranceList = listOf(),
        onClickNext = {}
    )
}
