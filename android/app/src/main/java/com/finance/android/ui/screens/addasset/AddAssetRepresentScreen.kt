package com.finance.android.ui.screens.addasset

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.R
import com.finance.android.domain.dto.response.BankAccountResponseDto
import com.finance.android.ui.components.*
import com.finance.android.utils.Response
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.AddAssetViewModel
import kotlinx.coroutines.launch

@Composable
fun AddAssetRepresentScreen(
    addAssetViewModel: AddAssetViewModel,
    onClickBack: () -> Unit,
    onClickNext: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        with(addAssetViewModel.getAddedAccountList()) {
            if (isNotEmpty()) {
                addAssetViewModel.onClickRepAccountItem(first().acNo)
            }
        }
    }
    when (val response = addAssetViewModel.checkHasRepAccount.value) {
        is Response.Success -> {
            if (!response.data) {
                Screen(
                    onClickBack = onClickBack,
                    onClickNext = {
                        coroutineScope.launch {
                            addAssetViewModel.registerRepAccount(onClickNext)
                        }
                    },
                    accountList = addAssetViewModel.getAddedAccountList(),
                    checkAccountNumber = addAssetViewModel.repAccountNumber.value,
                    onClickAccountItem = { addAssetViewModel.onClickRepAccountItem(it) }
                )
            } else {
                LaunchedEffect(Unit) {
                    onClickNext()
                }
            }
        }
        else -> AnimatedLoading()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onClickNext: () -> Unit,
    accountList: List<BankAccountResponseDto>,
    checkAccountNumber: String,
    onClickAccountItem: (accountNumber: String) -> Unit
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
            Spacer(modifier = modifier.height(20.dp))
            Text(
                modifier = modifier
                    .padding(start = dimensionResource(id = R.dimen.padding_medium).value.dp),
                text = stringResource(id = R.string.msg_select_rep_account),
                style = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp,
                    lineHeight = dimensionResource(id = R.dimen.font_size_medium).value.sp
                )
            )
            Spacer(modifier = modifier.height(20.dp))
            Box(modifier = modifier.weight(1.0f)) {
                LazyColumn(modifier = modifier) {
                    items(
                        count = accountList.size,
                        key = { idx -> idx },
                        itemContent = { idx ->
                            val item = accountList[idx]
                            AccountListItem_Select(
                                modifier = modifier.padding(
                                    horizontal = dimensionResource(id = R.dimen.padding_medium).value.dp,
                                    vertical = 8.dp
                                ),
                                contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.padding_medium)),
                                accountNumber = item.acNo,
                                balance = item.balance,
                                accountName = item.acName,
                                companyLogoPath = item.cpLogo,
                                acMain = item.acMain,
                                selected = checkAccountNumber == item.acNo,
                                onClickItem = { onClickAccountItem(item.acNo) }
                            )
                        }
                    )
                }
            }
            TextButton(
                onClick = onClickNext,
                text = stringResource(id = R.string.btn_confirm),
                buttonType = ButtonType.ROUNDED,
                modifier = Modifier.withBottomButton()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewScreen() {
    Screen(
        onClickBack = { },
        onClickNext = { },
        accountList = MutableList(5) {
            BankAccountResponseDto(
                acName = "acName",
                acNo = "acNo",
                balance = 10000,
                cpName = "cpName",
                cpLogo = "cpLogo",
                isRegister = false,
                acMain = 1,
                acType = 1
            )
        },
        checkAccountNumber = "acNo",
        onClickAccountItem = {}
    )
}
