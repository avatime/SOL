package com.finance.android.ui.screens

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.os.Build
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
import androidx.compose.ui.platform.LocalContext
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    fun launch() {
        homeViewModel.Load()
    }

    LaunchedEffect(Unit) {
        launch()
    }

    Scaffold(
        topBar = { TopBar(navController = navController) }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            when (val data = homeViewModel.getLoadState()) {
                is Response.Success -> {
                    HomeCardContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 0.dp,
                                start = dimensionResource(R.dimen.padding_medium),
                                end = dimensionResource(R.dimen.padding_medium),
                                bottom = dimensionResource(R.dimen.padding_medium)
                            )
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        navController = navController,
                        accData = (homeViewModel.accountList.value as Response.Success).data,
                        cardData = (homeViewModel.cardList.value as Response.Success).data
                    )
                    HomeCardContainer2(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 0.dp,
                                start = dimensionResource(R.dimen.padding_medium),
                                end = dimensionResource(R.dimen.padding_medium),
                                bottom = dimensionResource(R.dimen.padding_medium)
                            )
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(10)
                            ),
                        navController = navController
                    )
                }
                is Response.Loading -> {}
                else -> {}
            }
        }
    }
}

@Composable
fun HomeCardContainer(
    modifier: Modifier,
    navController: NavController,
    accData: MutableList<BankAccountResponseDto>,
    cardData: MutableList<CardInfoResponseDto>
) {
    Column(
        modifier = modifier
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
                text = "${accData.size + cardData.size + 1}",
                color = Color.Gray,
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.weight(1.0f))
            IconButton(
                onClick = {
                    navController.navigate(Const.Routes.ASSET)
                },
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
//        AccountListItem(
//            onClickRemit = {
//                navController.navigate("${Const.Routes.REMIT}/신한은행/1111/10")
//            }
//        )
        accData!!.forEach {
            AccountListItem_Remit(
                accountNumber = it.acNo,
                balance = it.balance,
                accountName = it.acName,
                companyLogoPath = it.cpLogo,
                onClickItem = { /*TODO*/ },
                onClickRemit = {
                    navController.navigate("${Const.Routes.REMIT}/${it.cpName}/${it.acNo}/${it.balance}")
                }
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        Divider()
        cardData!!.forEach {
            CardListItem_Arrow(
                cardName = it.cardName,
                cardImgPath = it.cardImgPath,
                onClickItem = {}
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        Divider()
        InsuranceListItem()
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun TopBar(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_medium).value.dp,
                vertical = 10.dp
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val onClick = { navController.navigate(Const.Routes.PEDOMETER) }

        if (Build.VERSION_CODES.Q <= Build.VERSION.SDK_INT) {
            val parState =
                rememberPermissionState(permission = Manifest.permission.ACTIVITY_RECOGNITION)
            if (!parState.status.isGranted) {
                PedometerOffStateButton(
                    onClick = onClick
                )
                return@Row
            }
        }

        if (Build.VERSION_CODES.N <= Build.VERSION.SDK_INT) {
            val manager =
                LocalContext.current.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager
            if (!manager.areNotificationsEnabled()) {
                PedometerOffStateButton(
                    onClick = onClick
                )
                return@Row
            }
        }

        PedometerOnStateButton(
            onClick = onClick
        )
    }
}

@Preview
@Composable
private fun PedometerOffStateButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable {
                onClick()
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_running_shoe),
            contentDescription = "running_shoe",
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "걸음 보기"
        )
    }
}

@Preview
@Composable
private fun PedometerOnStateButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable {
                onClick()
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "만보기 ON 상태다!!"
        )
    }
}
