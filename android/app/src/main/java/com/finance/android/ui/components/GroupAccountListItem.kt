package com.finance.android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R
import java.text.DecimalFormat

@Composable
fun GroupAccountListItem(
    paName: String,
    amount: Int,
    onClick : () -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 15.dp)
        .height(75.dp)
        .clickable { onClick()
        }, verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.ic_group_account)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = paName,fontSize = dimensionResource(R.dimen.account_like_name).value.sp, overflow = TextOverflow.Ellipsis,
                softWrap = false,
            )
            Spacer(modifier = Modifier.padding(3.dp))
            Text(text = DecimalFormat("#,###원").format(amount),fontSize = dimensionResource(R.dimen.account_like_name).value.sp)
        }
    }

}

