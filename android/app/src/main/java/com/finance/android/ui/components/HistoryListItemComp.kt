package com.finance.android.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.R
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Preview
@Composable
fun showHistoryList(
//    onClick : (year : Int, month : Int) -> Unit
    onClick : () -> Unit = {}
) {
    val currentMonth = remember { YearMonth.now() }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .height(700.dp)
            .background(
                color = MaterialTheme.colorScheme.background,
                RoundedCornerShape(dimensionResource(R.dimen.calendar_default) / 2)
            )
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Center
        ){
            Row(
                modifier = Modifier.padding(10.dp, top = 20.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    modifier = Modifier.clickable {
                        currentMonth.minusMonths(1)
                        onClick()
                    },
                    contentDescription = "",
                )
                Text(text = currentMonth.year.toString() + "년 " + currentMonth.monthValue.toString() + "월", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Image(
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        currentMonth.plusMonths(1)
                        onClick()
                    }
                )
            }
            Column() {
                repeat(30) {
                    HistoryItem()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryItem(
    date : LocalDateTime = LocalDateTime.now(),
    title : String = "사용처",
    amount : Int = 1000000000,
    moneyType : String = "원",
    type : String = "출금"
    ) {
    val dec = DecimalFormat("#,###")
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(

            ) {
                DateText(text = date.format(DateTimeFormatter.ofPattern("MM.dd")).toString())
            }
            Column(

            ) {
                BasicHistoryText(text = title)
                GrayHistoryText(date.format(DateTimeFormatter.ofPattern("HH:mm")).toString())
            }
        }
        Column(horizontalAlignment = Alignment.End) {
            MoneyHistoryText(text = dec.format(amount) + moneyType, minus = amount < 0)
            GrayHistoryText(type)
        }
    }
}

@Preview
@Composable
fun BasicHistoryText(
    text : String = "Basic"
) {
    Text( text = text)
}

@Preview
@Composable
fun DateText(
    text : String = "10.21"
) {
    Text( text = text, fontWeight = FontWeight.SemiBold)
}

@Preview
@Composable
fun GrayHistoryText(
    text : String = "Gray"
) {
    Text( text = text, color = Color.Gray, fontSize = 10.sp)
}

@Preview
@Composable
fun MoneyHistoryText(
    text : String = "Money",
    minus : Boolean = true
) {
    val sign : String = if(minus) "- " else "+ "
    Text( text = sign+text, fontWeight = FontWeight.SemiBold, color = if(minus) Color(0xff3A00FF) else Color(0xffFF0046))
}

@Preview
@Composable
fun test1() {
    MoneyHistoryText(minus = false)
}
