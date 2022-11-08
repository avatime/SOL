package com.finance.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
    account: String = "신한은행 1234567890",
    balance : String = "100,000,000원",
    buttonText : String = "이체",
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .height(200.dp)
            .clickable { onClick }
            .background(
                MaterialTheme.colorScheme.primary,
                RoundedCornerShape(dimensionResource(R.dimen.calendar_default) / 2),
            ),
        verticalArrangement = Arrangement.Center
    ) {
        Row(

        ) {
            Column(modifier = Modifier.padding(start = 23.dp)) {
                Text(text = title, fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
                if(isAccount) Text(text = account, fontSize = 10.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
                Text(text = balance, fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
        buttonText = "출금"
    )
}