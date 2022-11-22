package com.finance.android.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R
import com.finance.android.domain.dto.response.IsPaidResponseDto
import com.finance.android.ui.theme.Disabled
import java.text.DecimalFormat
import java.time.LocalDateTime

@Composable
fun DuesItem(
    paid: Boolean,
    name: String,
    dueDate: LocalDateTime,
    totalUser: Int,
    paidUser: Int,
    duesVal: Int,
    check: MutableList<IsPaidResponseDto>,
    onClickPay: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val progress: Float = paidUser.toFloat() / totalUser.toFloat()
    val text = remember {
        mutableStateOf("회비내기")
    }
    if (paid) {
        text.value = "완료"
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .animateContentSize()
    ) {
        Row {
            Column {
                Text(text = name, fontSize = 18.sp)
                Spacer(modifier = Modifier.padding(3.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${dueDate.monthValue}/${dueDate.dayOfMonth}",
                        color = Color(R.color.noActiveColor)
                    )
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
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(5.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(
                modifier = Modifier.clip(RoundedCornerShape(20.dp))
                    .clickable {
                        expanded = !expanded
                    }
                    .padding(10.dp)
            ) {
                Text(
                    text = "$paidUser/$totalUser 완료"
                )
                Spacer(modifier = Modifier.padding(3.dp))
                Text(text = "${DecimalFormat("#,###").format(duesVal * paidUser)}원")
                Spacer(modifier = Modifier.padding(5.dp))
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = if (expanded) R.drawable.down else R.drawable.up),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = { onClickPay() },
                text = text.value,
                buttonType = ButtonType.ROUNDED,
                enabled = !paid
            )
        }
        if (expanded) {
            check.forEach { data ->
                UserItem(
                    modifier = Modifier.padding(5.dp),
                    avatar = data.friendRes.pfImg,
                    name = data.friendRes.userName,
                    paid = data.status
                )
            }
        }
    }
}

@Composable
private fun UserItem(
    modifier: Modifier,
    avatar: String,
    name: String,
    paid: Boolean
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        if(avatar=="null"){
            Image(
                painter = painterResource(id = R.drawable.ic_contact_avatar),
                contentDescription = "",
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            androidx.compose.material.Text(
                text = name,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 1.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                if (paid) Icons.Filled.CheckCircle else Icons.Filled.Close,
                contentDescription = null,
                tint = if (paid) MaterialTheme.colorScheme.primary else Disabled
            )
        }
        else{
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(avatar)
                    .crossfade(true)
                    .build(),
                contentDescription = "연락처 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            androidx.compose.material.Text(
                text = name,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 1.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                if (paid) Icons.Filled.CheckCircle else Icons.Filled.Close,
                contentDescription = null,
                tint = if (paid) MaterialTheme.colorScheme.primary else Disabled
            )
        }
    }
}
