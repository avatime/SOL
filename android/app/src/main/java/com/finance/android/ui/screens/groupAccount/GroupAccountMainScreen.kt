package com.finance.android.ui.screens.groupAccount


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.ui.GroupAccountEmpty
import com.finance.android.ui.components.AnimatedLoading
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.GroupAccountListItem
import com.finance.android.ui.components.TextButton
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

    fun launch() {
        groupAccountViewModel.getGroupAccountData()
    }

    LaunchedEffect(Unit) {
        launch()
    }
    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.padding_medium))
                .verticalScroll(rememberScrollState())
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(10)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (val response = groupAccountViewModel.groupAccountData.value) {
                is Response.Failure -> Text(text = "실패")
                is Response.Loading -> AnimatedLoading()
                is Response.Success -> {
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
                        response.data.forEach {
                            val paId = it.paId
                            GroupAccountListItem(paName = it.paName, amount = it.amount, onClick = {
                                navController.navigate("${Const.GROUP_ACCOUNT_DETAIL_SCREEN}/${paId}")
                            })
                            groupAccountViewModel.paId.value = paId
                        }
                    }
                }
            } //when
        }//column


        TextButton(
            onClick = { navController.navigate(Const.GROUP_ACCOUNT_MAKE_SCREEN) },
            modifier = modifier.withBottomButton(),
            text = "모두의 통장 만들러 가기",
            buttonType = ButtonType.ROUNDED
        )
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.font_size_title_desc)))
    }//column

}




