package com.finance.android.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
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

// HistoryList 를 사용하기 위한 엔티티
data class HistoryEntity(
    val date : LocalDateTime,
    val name : String,
    val value : Int
)

@Preview
@Composable
fun showHistoryList(
    onClick : (year : Int, month : Int, type : String) -> Unit = { year: Int, month: Int, type : String -> {}},
    type : String = "계좌",
    historyList : List<HistoryEntity> = emptyList()
) {
    var currentMonth = remember { mutableStateOf(YearMonth.now()) }
    var currentMenu = remember { mutableStateOf("모두") }
    var list = Mu
    var isDropDownMenuExpanded = remember { mutableStateOf(false) }
    val menuList : List<String> = when(type) {
        "포인트" -> listOf("모두", "적립", "출금")
        else -> listOf("모두", "입금", "출금"
    }
    val moneyType = when(type) {
        "포인트" -> "포인트"
        else -> "원"
    }

    Column(
        modifier = Modifier
            .height(700.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                RoundedCornerShape(dimensionResource(R.dimen.calendar_default) / 2)
            )
    ) {
        Column(
            modifier = Modifier.padding(10.dp),table
            verticalArrangement = Arrangement.Center
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier.padding(10.dp, top = 20.dp, bottom = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.ic_back),
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .clickable {
                                currentMonth.value = currentMonth.value.minusMonths(1)
                                onClick(
                                    currentMonth.value.year,
                                    currentMonth.value.monthValue,
                                    currentMenu.value
                                )
                            },
                        contentDescription = "",
                    )
                    Text(text = currentMonth.value.year.toString() + "년 " + currentMonth.value.monthValue.toString() + "월", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Image(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .clickable {
                                currentMonth.value = currentMonth.value.plusMonths(1)
                                onClick(
                                    currentMonth.value.year,
                                    currentMonth.value.monthValue,
                                    currentMenu.value
                                )
                            }
                    )
                }
                Column(
                    modifier = Modifier
                        .clickable { isDropDownMenuExpanded.value = true }
                        .padding(10.dp)
                ) {
                    Text(text = currentMenu.value + " ▾")
                    DropdownMenu(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                RoundedCornerShape(7.dp)
                            ),
                        expanded = isDropDownMenuExpanded.value,
                        onDismissRequest = { isDropDownMenuExpanded.value = false }
                    ) {
                        repeat(3) {
                            if(currentMenu.value != menuList[it]) {
                                DropdownMenuItem(onClick = {
                                    currentMenu.value = menuList[it]
                                    isDropDownMenuExpanded.value = false
                                }) {
                                    Text(text = menuList[it])
                                }
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                .verticalScroll(rememberScrollState())
            ) {
                val list = historyList
                for (history in list) {
                    HistoryItem(
                        date = history.date,
                        title = history.name,
                        amount = history.value,
                        moneyType = moneyType,
                        type = if(history.value < 0) menuList[2] else menuList[1]
                    )
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
                verticalArrangement = Arrangement.spacedBy(5.dp)
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
    Text( text = text, color = Color.Gray, fontSize = 12.sp)
}

@Preview
@Composable
fun MoneyHistoryText(
    text : String = "Money",
    minus : Boolean = true
) {
    val sign : String = if(minus) "" else "+"
    Text( text = sign+text, fontWeight = FontWeight.SemiBold, color = if(minus) Color(0xff3A00FF) else Color(0xffFF0046))
}

@Preview
@Composable
fun test1() {
    MoneyHistoryText(minus = false)
}

@Preview
@Composable
fun test2() {
    val historyList = List(30){i -> HistoryEntity(LocalDateTime.now().minusDays(i.toLong()).minusHours(i.toLong()), "사용처 $i", if(i % 2 == 0) -100000000 else 100000000)}
    showHistoryList(historyList = historyList, type = "포인트")
}

@Preview
@Composable
fun test3() {
    val historyList = List(30){i -> HistoryEntity(LocalDateTime.now().minusDays(i.toLong()).minusHours(i.toLong()), "사용처 $i", if(i % 2 == 0) -100000000 else 100000000)}
    showHistoryList(historyList = historyList)
}
