package com.finance.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.R

@Preview
@Composable
fun UserBalanceInfo(
    title : String = "신한 주거래 S20",
    isAccount : Boolean = true,
    type : String = "계좌",
    account: String = "신한은행 1234567890",
    balance : String = "100,000,000원",
    onClick: () -> Unit = {}
) {
    val buttonText = when(type) {
        "포인트" -> "출금"
        else -> "이체"
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.padding_medium), bottom = dimensionResource(id = R.dimen.padding_medium), start = 10.dp, end = 10.dp)
            .height(200.dp)
            .background(
                MaterialTheme.colorScheme.primary,
                RoundedCornerShape(dimensionResource(R.dimen.calendar_default) / 2),
            ),
        verticalArrangement = Arrangement.Center
    ) {
         Column(
            modifier = Modifier.padding(start = 23.dp, end = 23.dp, bottom = 23.dp).fillMaxWidth().fillMaxHeight(),
             verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(text = title, fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
                if(isAccount) Text(text = account, fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.Normal)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = balance, fontSize = 25.sp, color = Color.White, fontWeight = FontWeight.Bold)
                TextButton(
                    onClick = { onClick() },
                    text = buttonText,
                    modifier = Modifier,
                    buttonType = ButtonType.CIRCULAR,
                    buttonColor = ButtonColor.WHITE
                )
            }
        }
    }
}

@Preview
@Composable
fun test () {
    UserBalanceInfo (
        title = "포인트",
        isAccount = false,
        balance = "100 포인트",
        type = "포인트"
    )
}