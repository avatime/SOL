package com.finance.android.ui.fragments

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.finance.android.R
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.viewmodels.FinanceDetailViewModel
import java.text.DecimalFormat


@Composable
fun StockDetailFragment(
    financeDetailViewModel: FinanceDetailViewModel = hiltViewModel(),
    fnName: String,
    onClose: () -> Unit,
    close: Int,
    per: Float
) {
    Scaffold(
        topBar = {
            BackHeaderBar(text = "주식 상세보기", modifier = Modifier, onClickBack = onClose)
        }
    )
    { innerPaddingModifier ->
        Column(
            modifier = Modifier.padding(top = innerPaddingModifier.calculateTopPadding())
        ) {
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_large)))
            Text(text = fnName,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            Row(modifier = Modifier
                .padding(3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = DecimalFormat("#,###원").format(close),
                    fontWeight = FontWeight.Bold,
                    fontSize = 35.sp,
                    modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_medium))
                )
                Text(
                    text = "$per%",
                    color = if (per > 0) Color(0xFFFF0000) else Color(0xFF3F51B5),
                    fontSize = 25.sp
                )
            }
        }
    }
}