package com.finance.android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.R
import com.finance.android.ui.theme.Disabled

@Composable
fun Empty(
    message: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search_off),
            modifier = Modifier.size(40.dp),
            contentDescription = "icon",
            tint = Disabled
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = message,
            color = Disabled,
            fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp
        )
    }
}

@Preview
@Composable
private fun PreviewEmpty() {
    Empty(message = "message")
}
