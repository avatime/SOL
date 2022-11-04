package com.finance.android.ui.screens.addasset

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.*
import com.finance.android.R
import com.finance.android.domain.dto.response.BankAccountResponseDto
import com.finance.android.domain.dto.response.CardInfoResponseDto
import com.finance.android.domain.dto.response.InsuranceInfoResponseDto
import com.finance.android.ui.components.*
import com.finance.android.ui.theme.Disabled
import com.finance.android.utils.Response
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.AddAssetViewModel

@Composable
fun AddAssetSelectScreen(
    modifier: Modifier = Modifier,
    addAssetViewModel: AddAssetViewModel,
    onClickBack: () -> Unit,
    onClickNext: () -> Unit
) {
    LaunchedEffect(Unit) {
        addAssetViewModel.init()
    }

    when (addAssetViewModel.getLoadState()) {
        is Response.Success -> {
            if (!addAssetViewModel.hasAssetToRegister()) {
                Box {
                    Loading(
                        modifier = modifier,
                        onClickBack = onClickBack
                    )
                    CustomDialog(
                        dialogType = DialogType.ERROR,
                        dialogActionType = DialogActionType.ONE_BUTTON,
                        title = stringResource(id = R.string.msg_hasnt_asset_to_register),
                        onPositive = onClickBack
                    )
                }
            } else {
                Screen(
                    modifier = modifier,
                    onClickBack = onClickBack,
                    onClickNext = {
                        addAssetViewModel.registerAsset()
                        onClickNext()
                    },
                    selectedAll = addAssetViewModel.selectedAll.value,
                    onClickSelectAll = { addAssetViewModel.onClickSelectAll() },
                    accountList = (addAssetViewModel.accountList.value as Response.Success).data,
                    accountCheckList = addAssetViewModel.accountCheckList,
                    onClickAccountItem = { addAssetViewModel.onClickAccountItem(it) },
                    cardList = (addAssetViewModel.cardList.value as Response.Success).data,
                    cardCheckList = addAssetViewModel.cardCheckList,
                    onClickCardItem = { addAssetViewModel.onClickCardItem(it) },
                    stockAccountList = (addAssetViewModel.stockAccountList.value as Response.Success).data,
                    stockAccountCheckList = addAssetViewModel.stockAccountCheckList,
                    onClickStockAccountItem = { addAssetViewModel.onClickStockAccountItem(it) },
                    insuranceList = (addAssetViewModel.insuranceList.value as Response.Success).data,
                    insuranceCheckList = addAssetViewModel.insuranceCheckList,
                    onClickInsuranceItem = { addAssetViewModel.onClickInsuranceItem(it) },
                    hasRepAccount = (addAssetViewModel.checkHasRepAccount.value as Response.Success).data
                )
            }
        }
        else -> {
            Loading(
                modifier = modifier,
                onClickBack = onClickBack
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun Loading(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit = {}
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            BackHeaderBar(
                text = stringResource(id = R.string.nav_add_asset),
                modifier = modifier,
                onClickBack = onClickBack,
                backgroundColor = MaterialTheme.colorScheme.surface
            )
        }
    ) {
        ConstraintLayout(
            modifier = modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
        ) {
            val (anim, text) = createRefs()
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ic_searching_asset))
            val progress by animateLottieCompositionAsState(
                composition,
                iterations = LottieConstants.IterateForever
            )

            LottieAnimation(
                modifier = modifier
                    .size(160.dp)
                    .constrainAs(anim) {
                        bottom.linkTo(text.top, margin = 20.dp)
                        centerHorizontallyTo(parent)
                    },
                composition = composition,
                progress = { progress }
            )

            Column(
                modifier = modifier.constrainAs(text) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.msg_loading_asset_title),
                    fontSize = dimensionResource(id = R.dimen.font_size_large).value.sp,
                    lineHeight = dimensionResource(id = R.dimen.font_size_large).value.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = modifier.height(5.dp))
                Text(
                    text = stringResource(id = R.string.msg_loading_asset_desc),
                    fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp,
                    lineHeight = dimensionResource(id = R.dimen.font_size_medium).value.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onClickNext: () -> Unit,
    selectedAll: Boolean,
    onClickSelectAll: () -> Unit,
    accountList: MutableList<BankAccountResponseDto>,
    accountCheckList: Array<MutableState<Boolean>>,
    onClickAccountItem: (index: Int) -> Unit,
    cardList: MutableList<CardInfoResponseDto>,
    cardCheckList: Array<MutableState<Boolean>>,
    onClickCardItem: (index: Int) -> Unit,
    stockAccountList: MutableList<BankAccountResponseDto>,
    stockAccountCheckList: Array<MutableState<Boolean>>,
    onClickStockAccountItem: (index: Int) -> Unit,
    insuranceList: MutableList<InsuranceInfoResponseDto>,
    insuranceCheckList: Array<MutableState<Boolean>>,
    onClickInsuranceItem: (index: Int) -> Unit,
    hasRepAccount: Boolean
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            BackHeaderBar(
                text = stringResource(id = R.string.nav_add_asset),
                modifier = modifier,
                onClickBack = onClickBack,
                backgroundColor = MaterialTheme.colorScheme.surface
            )
        }
    ) {
        Column(
            modifier = modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
        ) {
            var selectedTabIndex by remember { mutableStateOf(0) }
            Spacer(modifier = modifier.height(20.dp))

            Text(
                modifier = modifier
                    .padding(start = dimensionResource(id = R.dimen.padding_medium).value.dp),
                text = stringResource(
                    id = R.string.msg_find_asset,
                    accountList.size,
                    cardList.size,
                    stockAccountList.size,
                    insuranceList.size
                ),
                style = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp,
                    lineHeight = dimensionResource(id = R.dimen.font_size_medium).value.sp
                )
            )
            Spacer(modifier = modifier.height(20.dp))
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_medium).value.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                try {
                    Class
                        .forName("androidx.compose.material3.TabRowKt")
                        .getDeclaredField("ScrollableTabRowMinimumTabWidth")
                        .apply {
                            isAccessible = true
                        }
                        .set(this, 0f)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = modifier.weight(1.0f),
                    containerColor = MaterialTheme.colorScheme.surface,
                    edgePadding = 0.dp,
                    indicator = {},
                    divider = {}
                ) {
                    stringArrayResource(id = R.array.add_asset_tab_array)
                        .forEachIndexed { index, s ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                modifier = Modifier
                                    .padding(all = 5.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                                selectedContentColor = MaterialTheme.colorScheme.onSurface,
                                unselectedContentColor = Disabled,
                                text = {
                                    Text(
                                        text = s,
                                        fontSize = dimensionResource(id = R.dimen.font_size_tab_default).value.sp
                                    )
                                }
                            )
                        }
                }
                Spacer(modifier = modifier.width(10.dp))
                Row(
                    modifier = modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onClickSelectAll() }
                        .padding(all = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val color = if (selectedAll) MaterialTheme.colorScheme.primary else Disabled
                    Text(
                        text = stringResource(id = R.string.btn_all),
                        fontSize = dimensionResource(id = R.dimen.font_size_tab_default).value.sp,
                        color = color
                    )
                    Spacer(modifier = modifier.width(2.dp))
                    Icon(
                        Icons.Filled.CheckCircle,
                        contentDescription = stringResource(id = R.string.btn_all),
                        tint = color
                    )
                }
            }
            Box(modifier = modifier.weight(1.0f)) {
                when (selectedTabIndex) {
                    0 -> Account(
                        accountList = accountList,
                        accountCheckList = accountCheckList,
                        onClickAccountItem = onClickAccountItem
                    )
                    1 -> Card(
                        cardList = cardList,
                        cardCheckList = cardCheckList,
                        onClickCardItem = onClickCardItem
                    )
                    2 -> Stock(
                        stockAccountList = stockAccountList,
                        stockAccountCheckList = stockAccountCheckList,
                        onClickStockAccountItem = onClickStockAccountItem
                    )
                    else -> Insurance(
                        insuranceList = insuranceList,
                        insuranceCheckList = insuranceCheckList,
                        onClickInsuranceItem = onClickInsuranceItem
                    )
                }
            }
            var showSnackbar by remember { mutableStateOf(false) }
            if (showSnackbar) {
                TransientSnackbar(
                    onDismiss = { showSnackbar = false }
                ) {
                    Text(
                        text = stringResource(id = R.string.msg_snack_select_rep_account),
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }
            TextButton(
                onClick = {
                    if (!hasRepAccount && accountCheckList.all { c -> !c.value }) {
                        showSnackbar = true
                        return@TextButton
                    }
                    onClickNext()
                },
                text = stringResource(id = R.string.btn_confirm),
                buttonType = ButtonType.ROUNDED,
                modifier = Modifier.withBottomButton()
            )
        }
    }
}

@Composable
private fun Account(
    modifier: Modifier = Modifier,
    accountList: MutableList<BankAccountResponseDto>,
    accountCheckList: Array<MutableState<Boolean>>,
    onClickAccountItem: (index: Int) -> Unit
) {
    if (accountList.isEmpty()) {
        Empty(message = stringResource(id = R.string.msg_hasnt_account_to_register))
        return
    }
    LazyColumn(modifier = modifier) {
        items(
            count = accountList.size,
            key = { it },
            contentType = { true },
            itemContent = {
                val item = accountList[it]
                val checked = accountCheckList[it].value
                AccountListItem_Check(
                    contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.padding_medium)),
                    accountNumber = item.acNo,
                    balance = item.balance,
                    accountName = item.acName,
                    companyLogoPath = item.cpLogo,
                    checked = checked,
                    onClickItem = { onClickAccountItem(it) }
                )
            }
        )
    }
}

@Composable
private fun Card(
    modifier: Modifier = Modifier,
    cardList: MutableList<CardInfoResponseDto>,
    cardCheckList: Array<MutableState<Boolean>>,
    onClickCardItem: (index: Int) -> Unit
) {
    if (cardList.isEmpty()) {
        Empty(message = stringResource(id = R.string.msg_hasnt_card_to_register))
        return
    }
    LazyColumn(modifier = modifier) {
        items(
            count = cardList.size,
            key = { it },
            itemContent = {
                val item = cardList[it]
                val checked = cardCheckList[it].value
                CardListItem_Check(
                    contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.padding_medium)),
                    cardName = item.cardName,
                    cardImgPath = item.cardImgPath,
                    checked = checked,
                    onClickItem = { onClickCardItem(it) }
                )
            }
        )
    }
}

@Composable
private fun Stock(
    modifier: Modifier = Modifier,
    stockAccountList: MutableList<BankAccountResponseDto>,
    stockAccountCheckList: Array<MutableState<Boolean>>,
    onClickStockAccountItem: (index: Int) -> Unit
) {
    if (stockAccountList.isEmpty()) {
        Empty(message = stringResource(id = R.string.msg_hasnt_stock_account_to_register))
        return
    }
    LazyColumn(modifier = modifier) {
        items(
            count = stockAccountList.size,
            key = { it },
            itemContent = {
                val item = stockAccountList[it]
                val checked = stockAccountCheckList[it].value
                AccountListItem_Check(
                    contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.padding_medium)),
                    accountNumber = item.acNo,
                    balance = item.balance,
                    accountName = item.acName,
                    companyLogoPath = item.cpLogo,
                    checked = checked,
                    onClickItem = { onClickStockAccountItem(it) }
                )
            }
        )
    }
}

@Composable
private fun Insurance(
    modifier: Modifier = Modifier,
    insuranceList: MutableList<InsuranceInfoResponseDto>,
    insuranceCheckList: Array<MutableState<Boolean>>,
    onClickInsuranceItem: (index: Int) -> Unit
) {
    if (insuranceList.isEmpty()) {
        Empty(message = stringResource(id = R.string.msg_hasnt_insurance_to_register))
        return
    }
    LazyColumn(modifier = modifier) {
        items(
            count = insuranceList.size,
            key = { it },
            itemContent = {
                val item = insuranceList[it]
                val checked = insuranceCheckList[it].value
                InsuranceListItem_Check(
                    contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.padding_medium)),
                    insuranceName = item.isPdName,
                    fee = item.isPdFee,
                    myName = item.name,
                    isName = item.isName,
                    checked = checked,
                    onClickItem = { onClickInsuranceItem(it) }
                )
            }
        )
    }
}

@Preview
@Composable
private fun PreviewScreen() {
    Screen(
        onClickBack = { },
        onClickNext = { },
        selectedAll = false,
        onClickSelectAll = { },
        accountList = mutableListOf(),
        accountCheckList = arrayOf(),
        onClickAccountItem = {},
        cardList = mutableListOf(),
        cardCheckList = arrayOf(),
        onClickCardItem = {},
        stockAccountList = mutableListOf(),
        stockAccountCheckList = arrayOf(),
        onClickStockAccountItem = {},
        insuranceList = mutableListOf(),
        insuranceCheckList = arrayOf(),
        onClickInsuranceItem = {},
        hasRepAccount = true
    )
}

@Preview
@Composable
private fun PreviewAccount() {
    Account(
        modifier = Modifier.background(Color.White),
        accountList = MutableList(5) {
            BankAccountResponseDto(
                acName = "acName",
                acNo = "acNo",
                balance = 10000,
                cpName = "cpName",
                cpLogo = "cpLogo",
                isRegister = false
            )
        },
        accountCheckList = Array(5) {
            mutableStateOf(it % 2 == 0)
        },
        onClickAccountItem = {}
    )
}

@Preview
@Composable
private fun PreviewCard() {
    Card(
        modifier = Modifier.background(Color.White),
        cardList = MutableList(5) {
            CardInfoResponseDto(
                cardNumber = "123",
                cardName = "cardName",
                cardImgPath = "path",
                cardReg = true,
                isRegister = false
            )
        },
        cardCheckList = Array(5) {
            mutableStateOf(it % 2 == 0)
        },
        onClickCardItem = {}
    )
}

@Preview
@Composable
private fun PreviewStock() {
    Stock(
        modifier = Modifier.background(Color.White),
        stockAccountList = MutableList(5) {
            BankAccountResponseDto(
                acName = "acName",
                acNo = "acNo",
                balance = 10000,
                cpName = "cpName",
                cpLogo = "cpLogo",
                isRegister = false
            )
        },
        stockAccountCheckList = Array(5) {
            mutableStateOf(it % 2 == 0)
        },
        onClickStockAccountItem = {}
    )
}
