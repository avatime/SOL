package com.finance.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.ui.components.CardListItem

@Composable
fun AssetCardScreen(navController : NavController) {
    Column(modifier = Modifier
        .padding(top = dimensionResource(R.dimen.padding_small)))
    {
        AssetCardContainer(modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .background(color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10)),
            navController = navController
        )
    }
}

@Composable
fun AssetCardContainer(modifier: Modifier, navController: NavController) {
    Column(modifier = modifier
        .padding(dimensionResource(R.dimen.padding_medium)))
    {
        CardListItem()
        CardListItem()
    }
}