package com.finance.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun StockScreen(navController: NavController) {
    Column(modifier = Modifier) {
        StockContainer()
        StockContainer()
    }
}

@Composable
fun StockContainer() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://images.samsung.com/kdp/aboutsamsung/brand_identity/logo/256_144_3.png?\$512_288_PNG\$")
                .crossfade(true)
                .build(),
            contentDescription = "기업로고",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
        )
        Spacer(modifier = Modifier
            .weight(.05f))
        Text(text = "삼성전자",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
            )
        Spacer(modifier = Modifier
            .weight(1f))
        Column(horizontalAlignment = Alignment.End) {
            Text(text = "+0.3%",
                color = Color(0xFFFF0000),
                fontSize = 19.sp
            )
            Text(text = "59,400원",
                fontSize = 15.sp)
        }
    }
}

@Preview
@Composable
fun F() {
    StockContainer()
}