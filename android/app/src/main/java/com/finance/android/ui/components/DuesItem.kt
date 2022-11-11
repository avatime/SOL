package com.finance.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.R
import java.text.DecimalFormat

@Composable
fun DuesItem(paid : Boolean, name: String, dueDate: String, totalUser: Int, paidUser: Int, duesVal: Int, onClick : ()-> Unit) {

    val progress: Float = paidUser.toFloat() / totalUser.toFloat()
    val text = remember {
        mutableStateOf("회비내기")
    }
    if (paid) {
        text.value = "완료"
    }

    Column(
        verticalArrangement = Arrangement.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {

        Row() {
            Column() {
                Text(text = name, fontSize = 18.sp)
                Spacer(modifier = Modifier.padding(3.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = dueDate, color = Color(R.color.noActiveColor))
                    Spacer(modifier = Modifier.padding(3.dp))
                    Text(
                        text = "${DecimalFormat("#,###").format(duesVal)}원",
                        color = Color(R.color.noActiveColor)
                    )
                }
            }


        }
        Spacer(modifier = Modifier.padding(10.dp))
        LinearProgressIndicator(
            progress = progress,
            color = MaterialTheme.colorScheme.primary, modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(5.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Row() {
                Text(text = "${paidUser}/${totalUser} 완료")
                Spacer(modifier = Modifier.padding(3.dp))
                Text(text = "${DecimalFormat("#,###").format(duesVal * paidUser)}원")
            }
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = { onClick() }, text = text.value, buttonType = ButtonType.ROUNDED, enabled = !paid)
        }

    }
}

//@Preview
//@Composable
//fun previewDuesItem() {
//    DuesItem(
//        name = "돈내라내라내라",
//        dueDate = "2022년 3월 9일",
//        totalUser = 6,
//        paidUser = 2,
//        duesVal = 10000,
//        onClick = {}
//    )
//}