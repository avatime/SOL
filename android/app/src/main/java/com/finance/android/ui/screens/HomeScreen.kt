package com.finance.android.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.domain.dto.response.BankAccountResponseDto
import com.finance.android.domain.dto.response.CardInfoResponseDto
import com.finance.android.ui.components.*
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        homeViewModel.load()
    }

    Scaffold(
        topBar = {},
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
        ) {
            when (homeViewModel.getLoadState()) {
                is Response.Success -> Screen(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState()),
                    onClickAsset = {
                        navController.navigate(Const.Routes.ASSET)
                    },
                    accountList = (homeViewModel.accountList.value as Response.Success).data,
                    onClickAccountItem = {},
                    onClickAccountRemit = { cpName, acNo, balance ->
                        navController.navigate("${Const.Routes.REMIT}/$cpName/$acNo/$balance")
                    },
                    cardList = (homeViewModel.cardList.value as Response.Success).data,
                    onClickCardItem = {}
                )
                else -> AnimatedLoading()
            }

        }
    }
}

@Composable
private fun Screen(
    modifier: Modifier,
    onClickAsset: () -> Unit,
    accountList: MutableList<BankAccountResponseDto>,
    onClickAccountItem: () -> Unit,
    onClickAccountRemit: (cpName: String, acNo: String, balance: Int) -> Unit,
    cardList: MutableList<CardInfoResponseDto>,
    onClickCardItem: () -> Unit
) {
    Column(modifier = modifier) {
        HomeCardContainer(
            onClickAsset = onClickAsset,
            accountList = accountList,
            onClickAccountItem = onClickAccountItem,
            onClickAccountRemit = onClickAccountRemit,
            cardList = cardList,
            onClickCardItem = onClickCardItem
        )
    }

//        HomeCardContainer2(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(dimensionResource(R.dimen.padding_medium))
//                .background(
//                    color = MaterialTheme.colorScheme.surface,
//                    shape = RoundedCornerShape(10)
//                ),
//            navController = navController
//        )
}

@Composable
fun HomeCardContainer(
    onClickAsset: () -> Unit,
    accountList: MutableList<BankAccountResponseDto>,
    onClickAccountItem: () -> Unit,
    onClickAccountRemit: (cpName: String, acNo: String, balance: Int) -> Unit,
    cardList: MutableList<CardInfoResponseDto>,
    onClickCardItem: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_medium))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.card_radius)))
            .background(MaterialTheme.colorScheme.surface)
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "자산",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "${accountList.size + accountList.size}",
                color = Color.Gray,
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.weight(1.0f))
            IconButton(
                onClick = onClickAsset,
                modifier = Modifier.size(30.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.arrow_forward_ios),
                    contentDescription = "forwardArrow",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
        accountList.forEach {
            AccountListItem_Remit(
                accountNumber = it.acNo,
                balance = it.balance,
                accountName = it.acName,
                companyLogoPath = it.cpLogo,
                onClickItem = onClickAccountItem,
                onClickRemit = {
                    onClickAccountRemit(it.cpName, it.acNo, it.balance)
                }
            )

        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        Divider()
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        cardList.forEach {
            CardListItem_Arrow(
                cardName = it.cardName,
                cardImgPath = it.cardImgPath,
                onClickItem = onClickCardItem
            )
        }
//        Divider()
//        InsuranceListItem()
    }
}

@Composable
fun HomeCardContainer2(modifier: Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "쌀",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.weight(1.0f))
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(30.dp)) {
                Image(
                    painter = painterResource(R.drawable.arrow_forward_ios),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.ssal),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(R.dimen.padding_medium)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = { /*TODO*/ },
                    text = "만보기",
                    modifier = Modifier
                        .height(40.dp)
                        .width(120.dp)
                        .padding(start = 20.dp),
                    buttonType = ButtonType.CIRCULAR,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.weight(1.0f))
                TextButton(
                    onClick = { /*TODO*/ },
                    text = "출석체크",
                    modifier = Modifier
                        .height(40.dp)
                        .width(120.dp)
                        .padding(end = 20.dp),
                    buttonType = ButtonType.CIRCULAR,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewHomeCardContainer() {
    HomeCardContainer(
        onClickAsset = { /*TODO*/ },
        accountList = MutableList(5) {
            BankAccountResponseDto(
                acNo = "acNo",
                acName = "acName",
                balance = 100,
                cpLogo = "",
                cpName = "cpName",
                isRegister = true
            )
        },
        onClickAccountItem = { /*TODO*/ },
        onClickAccountRemit = { _, _, _ -> },
        cardList = MutableList(5) {
            CardInfoResponseDto(
                cardNumber = "cardNumber",
                cardName = "cardName",
                cardImgPath = "",
                isRegister = true
            )
        }
    ) {
    }
}
