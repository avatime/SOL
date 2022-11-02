package com.finance.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.ui.components.AccountListItem

@Composable
fun AssetStockScreen(navController: NavController) {
    Column(modifier = Modifier
        .padding(top = dimensionResource(R.dimen.padding_medium)))
    {
        AssetStockContainer(modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .background(color = Color.White, shape = RoundedCornerShape(10)),
            navController = navController
        )
    }
}

@Composable
fun AssetStockContainer(modifier: Modifier, navController: NavController) {
    Column(modifier = modifier
        .padding(dimensionResource(R.dimen.padding_medium)))
    {
        AccountListItem()
        AccountListItem()
    }
}