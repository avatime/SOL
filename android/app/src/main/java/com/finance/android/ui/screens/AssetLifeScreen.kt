package com.finance.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.ui.components.InsuranceListItem

@Composable
fun AssetLifeScreen(navController: NavController) {
    Column(modifier = Modifier
        .padding(top = dimensionResource(R.dimen.padding_small)))
    {
        AssetLifeContainer(modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(10)),
            navController = navController)
        AssetLifeContainer2(modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(15)))
    }
}

@Composable
fun AssetLifeContainer(modifier: Modifier, navController: NavController) {
    Column(modifier = modifier
        .padding(dimensionResource(R.dimen.padding_medium)))
    {
        InsuranceListItem()
        InsuranceListItem()
        InsuranceListItem()
    }
}

@Composable
fun AssetLifeContainer2(modifier: Modifier) {
    Column(modifier = modifier
        .padding(dimensionResource(R.dimen.padding_medium)))
    {
        Row {
            Text(text = "신한라이프",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(end = 16.dp))
            Text(text = "11월 납입보험료")
        }
        Text(text = "30,000원",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
        modifier = Modifier.padding(top = 24.dp, bottom = 24.dp, start = 8.dp))
    }
}