package com.finance.android.ui.fragments

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R
import com.finance.android.domain.dto.response.InsuranceDetailResponseDto
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.utils.Response
import com.finance.android.viewmodels.InsuranceDetailViewModel

@Composable
fun InsuranceDetailFragment(
    insuranceDetailViewModel: InsuranceDetailViewModel = hiltViewModel(),
    isId: Int,
    name: String,
    onClose: () -> Unit
) {
    LaunchedEffect(Unit) {
        insuranceDetailViewModel.load(isId)
    }
    Scaffold(
        topBar = {
            BackHeaderBar(text = "보험", modifier = Modifier, onClickBack = onClose)
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
                    contentDescription = null,
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
            when (insuranceDetailViewModel.getLoadState()) {
                is Response.Success -> {
                    ContentList(
                        accData = (insuranceDetailViewModel.insuranceDetail.value as Response.Success).data
                    )
                }
                is Response.Loading -> {}
                else -> {}
            }
        }
    }
}

@Composable
fun ContentList(
    accData: InsuranceDetailResponseDto
) {
    Column(
        modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium) / 2)
    ) {
        accData.detail.forEach {
            val text = it.replace("\n\n", "\n").split("\n")
            val expanded = remember { mutableStateOf(false) }
            val expandable = 1 < text.size

            Column(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(R.dimen.padding_medium) / 2,
                        horizontal = 15.dp / 2
                    ).animateContentSize()
            ) {
                Row(
                    modifier =
                    if (expandable) Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp / 2)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable { expanded.value = !expanded.value }
                    else Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = text[0],
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    if (expandable) {
                        Image(
                            modifier = Modifier
                                .size(24.dp),
                            painter = painterResource(id = if (expanded.value) R.drawable.up else R.drawable.down),
                            contentDescription = null
                        )
                    }
                }
                if (expanded.value && expandable) {
                    Divider(
                        modifier = Modifier.padding(
                            vertical = dimensionResource(R.dimen.padding_medium) / 2,
                            horizontal = 15.dp
                        ),
                        color = Color(0xFFE5D8FD)
                    )
                }
                if (expanded.value && expandable) {
                    Text(
                        modifier = Modifier.padding(horizontal = 15.dp),
                        text = text.slice(1 until text.size).joinToString("\n")
                    )
                }
            }
            Divider(
                modifier = Modifier.padding(
                    vertical = dimensionResource(R.dimen.padding_medium) / 2,
                    horizontal = 15.dp
                )
            )
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
    Column(
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
