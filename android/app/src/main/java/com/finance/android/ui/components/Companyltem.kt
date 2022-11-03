package com.finance.android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun CompanyItem(cpName: String, cpLogo: String) {

    Row(modifier = Modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(cpLogo)
                .crossfade(true)
                .build(), contentDescription = "회사 로고", contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(70.dp)
                .height(70.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(text = cpName)


    }

}