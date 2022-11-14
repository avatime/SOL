package com.finance.android.ui.screens.remit

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.domain.dto.response.BankInfoResponseDto
import com.finance.android.ui.components.AccountLikeItem
import com.finance.android.ui.components.AnimatedLoading
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.viewmodels.RemitViewModel

@Composable
fun RecoScreen(
    remitViewModel: RemitViewModel,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    fun launch() {
        remitViewModel.getRecommendedAccountData()
    }

    LaunchedEffect(Unit) {
        launch()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Recent(
            remitViewModel = remitViewModel,
            navController = navController
        )
    }
}

@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
private fun Recent(
    remitViewModel: RemitViewModel,
    navController: NavController
) {
    val expanded = remember { mutableStateOf(false) }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.padding(8.dp))

        when (remitViewModel.getLoadRecommendation()) {
            is Response.Failure -> Text(text = "실패")
            is Response.Loading -> AnimatedLoading(text = "가져오고 있어요")
            is Response.Success -> {
                val response = remitViewModel.recentMyAccountData.value as Response.Success
                Column(
                    modifier = Modifier.animateContentSize()
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { expanded.value = !expanded.value }
                            .padding(
                                horizontal = 15.dp,
                                vertical = 15.dp
                            )
                    ) {
                        Text(
                            text = "내 계좌",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            modifier = Modifier.padding(end = 10.dp),
                            text = if (expanded.value) "${response.data.size}개" else "+${response.data.size}개",
                            color = Color.Gray
                        )
                        Image(
                            modifier = Modifier
                                .size(24.dp),
                            painter = painterResource(id = if (expanded.value) R.drawable.up else R.drawable.down),
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))

                    response.data.forEach {
                        if (it.acNo != remitViewModel.accountNumber) {
                            AnimatedVisibility(visible = expanded.value, enter = fadeIn(animationSpec = tween(1000)), exit = fadeOut()) {
                                AccountLikeItem(
                                    bkStatus = it.bkStatus,
                                    cpLogo = it.cpLogo,
                                    name = it.acName,
                                    accountNumber = it.acNo,
                                    cpName = it.cpName,
                                    onClickBookmark = {
                                        remitViewModel.onClickAccountBookmark(it)
                                    },
                                    onClickItem = {
                                        remitViewModel.onClickReceiveBank(
                                            BankInfoResponseDto(
                                                cpCode = remitViewModel.cpCode.value,
                                                cpLogo = it.cpName,
                                                cpName = it.cpName
                                            )
                                        )
                                        remitViewModel.validReceiveAccountNumber.value = it.acNo
                                        navController.navigate(Const.INPUT_MONEY_SCREEN)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))

        when (remitViewModel.getLoadRecommendation()) {
            is Response.Failure -> Text(text = "실패")
            is Response.Loading -> {}
            is Response.Success -> {
                val response2 = remitViewModel.recommendedAccountData.value as Response.Success
                Text(
                    text = "최근 보낸 계좌",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 10.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                response2.data.forEach {
                    AccountLikeItem(
                        bkStatus = it.bkStatus,
                        cpLogo = it.cpLogo,
                        name = it.acReceive,
                        accountNumber = it.acNo,
                        cpName = it.cpName,
                        onClickBookmark = { remitViewModel.onClickAccountBookmark(it) },
                        onClickItem = {
                            remitViewModel.onClickReceiveBank(
                                BankInfoResponseDto(
                                    cpCode = remitViewModel.cpCode.value,
                                    cpLogo = it.cpName,
                                    cpName = it.cpName
                                )
                            )
                            remitViewModel.validReceiveAccountNumber.value = it.acNo
                            navController.navigate(Const.INPUT_MONEY_SCREEN)
                        }
                    )
                }
            }
        }
    }
}
