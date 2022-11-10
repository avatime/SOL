package com.finance.android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontLoadingStrategy.Companion.Async
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R
import java.text.DecimalFormat

@Composable
fun CardDetailComp(
    modifier: Modifier,
    cdName: String, // 카드 이름
    cdImgPath: String, // 카드 이미지
    cdNo: String, // 카드 번호
    balance: Int, // 월 청구 금액
) {
    Column(
        modifier = modifier
            .padding(18.dp)
    ) {
        Text(text = cdName,
            color = MaterialTheme.colorScheme.surface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "$cdNo",
            color = MaterialTheme.colorScheme.surface,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )
        Row(
            modifier = Modifier.padding(
                top = dimensionResource(R.dimen.padding_medium),
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = DecimalFormat("#,###원").format(balance),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.surface
            )
            Spacer(modifier = Modifier.weight(1.0f))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cdImgPath)
                    .crossfade(true)
                    .build(),
                contentDescription = "cardImage",
                modifier = modifier
                    .size(60.dp)
                    .padding(end = dimensionResource(R.dimen.padding_small))
            )
        }
    }
}