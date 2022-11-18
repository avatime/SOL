package com.finance.android.ui.screens.groupAccount


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.ui.GroupAccountEmpty
import com.finance.android.ui.components.*
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.GroupAccountViewModel

@Composable
fun GroupAccountMainScreen(
    navController: NavController,
    groupAccountViewModel: GroupAccountViewModel,
    modifier: Modifier
) {
    val isShowError = remember { mutableStateOf(false) }


    if (isShowError.value) {
        CustomDialog(
            dialogType = DialogType.ERROR,
            dialogActionType = DialogActionType.ONE_BUTTON,
            title = "대표계좌부터 설정해주세요",
            onPositive = { isShowError.value = false })
    }

    fun launch() {
        groupAccountViewModel.getGroupAccountData()
        groupAccountViewModel.duesVal.value = 0
    }
    LaunchedEffect(Unit) {
        launch()
    }

    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                .padding(top = dimensionResource(R.dimen.padding_medium), bottom = 0.dp)
                .weight(1f)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(20.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (val response = groupAccountViewModel.groupAccountData.value) {
                is Response.Failure -> Text(text = "실패")
                is Response.Loading -> AnimatedLoading()
                is Response.Success -> {
                    Log.i("group", "모두의통장들${response.data}")

                    if (response.data.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 25.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            GroupAccountEmpty(modifier = modifier)
                        }

                    } else {

                        LazyColumn {
                            items(count = response.data.size, key = { it }, itemContent = {
                                val item = response.data[it]
                                val paId = item.paId
                                GroupAccountListItem(
                                    paName = item.paName,
                                    amount = item.amount,
                                    onClick = {
                                        navController.navigate(Const.GROUP_ACCOUNT_DETAIL_SCREEN)
                                        groupAccountViewModel.paId.value = paId
                                        Log.i("group", "모통 paId $paId")
                                    })

                            })
                        }

                    }
                }
            } //when
        }//column
        TextButton(
            text = "모두의 통장 만들러 가기",
            onClick = {
                groupAccountViewModel.OKtext.value = "모두의 통장을 개설했습니다."
                groupAccountViewModel.getHasRepresentAccount(onSuccess = {navController.navigate(
                    Const.GROUP_ACCOUNT_MAKE_SCREEN
                )}, onFail = {isShowError.value = true})
            },
            modifier = Modifier.withBottomButton(),
            buttonType = ButtonType.ROUNDED
        )
    }//column


}








