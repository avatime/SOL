package com.finance.android.ui.screens.addasset

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
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
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
import com.finance.android.ui.components.*
import com.finance.android.ui.theme.Disabled
import com.finance.android.utils.Response
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.AddAssetViewModel
import kotlinx.coroutines.NonDisposableHandle.parent

@Composable
fun AddAssetSelectScreen(
    modifier: Modifier = Modifier,
    addAssetViewModel: AddAssetViewModel,
    onClickBack: () -> Unit,
    onClickNext: () -> Unit
) {
    LaunchedEffect(Unit) {
        addAssetViewModel.createAssetAndLoad()
    }

    when (addAssetViewModel.getLoadState()) {
        is Response.Success -> Screen(
            modifier = modifier,
            onClickBack = onClickBack,
            onClickNext = onClickNext,
            selectedAll = addAssetViewModel.selectedAll.value,
            onClickSelectAll = { addAssetViewModel.onClickSelectAll() },
            accountList = (addAssetViewModel.accountList.value as Response.Success).data,
            accountCheckList = addAssetViewModel.accountCheckList,
            onClickAccountItem = { addAssetViewModel.onClickAccountItem(it) },
            cardList = (addAssetViewModel.cardList.value as Response.Success).data,
            cardCheckList = addAssetViewModel.cardCheckList,
            onClickCardItem = { addAssetViewModel.onClickCardItem(it) }
        )
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
    onClickCardItem: (index: Int) -> Unit
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
                text = stringResource(id = R.string.msg_find_asset, accountList.size, cardList.size),
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
            Box(
                modifier = modifier
                    .weight(1.0f)
            ) {
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
                    2 -> Stock()
                    else -> Insurance()
                }
            }
            TextButton(
                onClick = onClickNext,
                text = stringResource(id = R.string.btn_add_asset),
                buttonType = ButtonType.ROUNDED,
                modifier = Modifier.withBottomButton()
            )
        }
    }
}

@Composable
private fun Account(
    accountList: MutableList<BankAccountResponseDto>,
    accountCheckList: Array<MutableState<Boolean>>,
    onClickAccountItem: (index: Int) -> Unit,
) {
    LazyColumn() {
        items(
            count = accountList.size,
            key = { it },
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
    cardList: MutableList<CardInfoResponseDto>,
    cardCheckList: Array<MutableState<Boolean>>,
    onClickCardItem: (index: Int) -> Unit,
) {
    LazyColumn() {
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
private fun Stock() {
    Column {
        Text(text = "Stock")
    }
}

@Composable
private fun Insurance() {
    Column {
        Text(text = "Insurance")
    }
}

@Preview
@Composable
private fun PreviewScreen() {
    Screen(
        onClickBack = { /*TODO*/ },
        onClickNext = { /*TODO*/ },
        selectedAll = false,
        onClickSelectAll = { /*TODO*/ },
        accountList = mutableListOf(),
        accountCheckList = arrayOf(),
        onClickAccountItem = {},
        cardList = mutableListOf(),
        cardCheckList = arrayOf(),
        onClickCardItem ={}
    )
}

@Preview
@Composable
private fun PreviewAccount() {
    Account(
        accountList = MutableList(5) {
            BankAccountResponseDto(
                acName = "acName",
                acNo = "acNo",
                balance = 10000,
                cpName = "cpName",
                cpLogo = "cpLogo",
                acReg = true
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
        cardList = MutableList(5) {
            CardInfoResponseDto(
                cardName = "cardName",
                cardImgPath = "path"
            )
        },
        cardCheckList = Array(5) {
            mutableStateOf(it % 2 == 0)
        },
        onClickCardItem = {}
    )
}
