package com.finance.android.ui.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.ui.components.BackHeaderBar

@Composable
fun InsuranceDetailFragment(
    name: String,
    onClose: () -> Unit
) {
    Scaffold(
        topBar = {
            BackHeaderBar(text = "보험입니다", modifier = Modifier, onClickBack = onClose)
        }
    ) { innerPaddingModifier ->
        Column(
            modifier = Modifier
                .padding(top = innerPaddingModifier.calculateTopPadding())
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .background(color = Color(0xFFE5D8FD))
                    .fillMaxWidth()
                    .padding(end = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(50.dp))
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://gamulbucket2022.s3.ap-northeast-2.amazonaws.com/finance/shinhanLife.png")
                        .crossfade(true)
                        .build(),
                    contentDescription = "기업로고",
                    contentScale = ContentScale.Crop
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = 12.dp),
                    text = name.replace("(", "\n("),
                    fontWeight = FontWeight.Bold,
                    fontSize = 23.sp,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier
                        .padding(top = 30.dp, bottom = 30.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Item(
                        modifier = Modifier.weight(1f),
                        data = "https://www.shinhanlife.co.kr/bizxpress/common/__media/ico_protection_lg.png",
                        title = "납입방법",
                        detail = "월납"
                    )
                    Item(
                        modifier = Modifier.weight(1f),
                        data = "https://www.shinhanlife.co.kr/bizxpress/common/__media/ico_3color_rg4.png",
                        title = "가입나이",
                        detail = "15세~70세"
                    )
                    Item(
                        modifier = Modifier.weight(1f),
                        data = "https://www.shinhanlife.co.kr/bizxpress/common/__media/ico_100_lg.png",
                        title = "보험기간",
                        detail = "10년, 20년"
                    )
                }
            }
        }
    }
}

@Composable
fun Item(
    modifier: Modifier,
    data: String,
    title: String,
    detail: String
) {
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        )
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier,
            text = detail,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}
