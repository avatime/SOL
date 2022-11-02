package com.finance.android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R

@Composable
fun AccountListItem() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = dimensionResource(R.dimen.padding_medium)),
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
            Text(text = "나라사랑행복계좌")
            Text(text = "10,000원", fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.weight(1.0f))
        TextButton(onClick = { /*TODO*/ },
            text = "송금",
            modifier = Modifier
                .height(30.dp)
                .width(50.dp)
                .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
            buttonType = ButtonType.ROUNDED,
            fontSize = 10.sp
        )
    }
}