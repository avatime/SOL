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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
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
    navController: NavController,
    expanded: MutableState<Boolean>
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Recent(
            remitViewModel = remitViewModel,
            navController = navController,
            expanded = expanded
        )
    }
}

@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
private fun Recent(
    remitViewModel: RemitViewModel,
    navController: NavController,
    expanded: MutableState<Boolean>
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

        when (remitViewModel.getLoadRecommendation()) {
            is Response.Failure -> Text(text = "실패")
            is Response.Loading -> AnimatedLoading(text = "가져오고 있어요")
            is Response.Success -> {
                val response = remitViewModel.recentMyAccountData.value as Response.Success
                Column(
                    modifier = Modifier.animateContentSize()
                ) {
                    if (response.data.size > 1) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { expanded.value = !expanded.value }
                                .padding(top = 5.dp)
                                .fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "내 계좌",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium), end = 10.dp)
                            )
                            Text(
                                text = "${response.data.size - 1}",
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.weight(1f))

                            Image(
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(end = 25.dp),
                                painter = painterResource(id = if (expanded.value) R.drawable.up else R.drawable.down),
                                contentDescription = null
                            )
                        }
                    }

                    response.data.forEach {
                        if (it.acNo != remitViewModel.accountNumber) {
                            AnimatedVisibility(visible = expanded.value, enter = fadeIn(animationSpec = tween(1500)), exit = fadeOut()) {
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
        Spacer(modifier = Modifier.padding(6.dp))

        when (remitViewModel.getLoadRecommendation()) {
            is Response.Failure -> Text(text = "실패")
            is Response.Loading -> {}
            is Response.Success -> {
                val response2 = remitViewModel.recommendedAccountData.value as Response.Success
                Text(
                    text = "최근 보낸 계좌",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium))
                )
                Spacer(modifier = Modifier.padding(4.dp))
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
