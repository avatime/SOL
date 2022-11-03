package com.finance.android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun CompanyItem(cpName: String, cpLogo: String) {

    Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(cpLogo)
                .crossfade(true)
                .build(), contentDescription = "회사 로고", contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(text = "${cpName}은행")


    }

}

@Preview
@Composable
fun PreviewCompanyItem() {
    CompanyItem(cpName = "우리", cpLogo = "https://w.namu.la/s/8e3ea802358c827ac96c128113c80ecb4529d429f9902973d5adb2d72b95bacf5888b8939beefff4ff0e78da8a0ed6aeb13d04b869c508f7cd4bbc51567b0da8ff1df5048908ecf179522e185514ba4d47afa717c57c3e039968f7c87aa2c7e5" )

}