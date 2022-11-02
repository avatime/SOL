package com.finance.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R
import com.finance.android.utils.Const

@Composable
fun InsuranceListItem() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = dimensionResource(R.dimen.padding_medium), bottom = dimensionResource(R.dimen.padding_small)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png")
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Column (modifier = Modifier
            .padding(start = 8.dp)) {
            Text(text = "신종단체상해보험", fontSize = 12.sp)
            Text(text = "보험료: 10,000원", fontWeight = FontWeight.Bold)
            Text(text = "계약자: 홍길동|피보험자: 홍길동", fontSize = 10.sp)
        }
        Spacer(modifier = Modifier.weight(1.0f))
        IconButton(onClick = { },
            modifier = Modifier.size(30.dp)) {
            Image(painter = painterResource(R.drawable.arrow_forward_ios),
                contentDescription = "forwardArrow",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp),
            )
        }
    }
}