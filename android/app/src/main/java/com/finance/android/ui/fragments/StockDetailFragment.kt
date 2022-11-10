package com.finance.android.ui.fragments

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockDetailFragment(
    financeDetailViewModel: FinanceDetailViewModel = hiltViewModel(),
    onClose: () -> Unit
) {
    LaunchedEffect(Unit) {
        financeDetailViewModel.launch()
    }

    Scaffold(
        topBar = {
            BackHeaderBar(
                onClickBack = onClose,
                text = "",
                backgroundColor = MaterialTheme.colorScheme.surface
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(top = it.calculateTopPadding())
        ) {
            if (financeDetailViewModel.loading.value) {

            }
        }
    }
}

@Composable
private fun Screen(
    fnName: String,
    close: Int,
    per: Float
) {
    Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_large)))
    Text(
        text = fnName,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp
    )
    Row(
        modifier = Modifier
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
