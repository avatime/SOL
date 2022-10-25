package com.finance.android.ui.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.R

@Preview
@Composable
fun HomeScreen() {
    CardContainer(modifier = Modifier
        .fillMaxWidth()
        .padding(
            top = dimensionResource(R.dimen.padding_medium),
            start = dimensionResource(R.dimen.padding_medium),
            end = dimensionResource(R.dimen.padding_medium)
        )
        .background(color = Color.Gray, shape = RoundedCornerShape(15))
        .height(200.dp)
        )
}

@Composable
fun CardContainer(modifier: Modifier) {
    Column(modifier = modifier
        .padding(dimensionResource(R.dimen.padding_medium))
        ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "자산",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.weight(1.0f))
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(20.dp)) {
                Image(painter = painterResource(R.drawable.arrow_forward_ios), contentDescription = null)
            }
        }
    }
}