package com.finance.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.ui.components.AccountListItem

@Composable
fun AssetBankScreen(navController: NavController) {
    Column(modifier = Modifier
        .padding(top = dimensionResource(R.dimen.padding_medium)))
    {
        AssetBankCardContainer(modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .background(color = Color.White, shape = RoundedCornerShape(10)),
            navController = navController
        )
    }
}

@Composable
fun AssetBankCardContainer(modifier: Modifier, navController: NavController) {
    Column(modifier = modifier
        .padding(dimensionResource(R.dimen.padding_medium)))
    {
        AccountListItem()
        AccountListItem()
        AccountListItem()
        AccountListItem()
    }
}