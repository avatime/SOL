package com.finance.android.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance.android.R
import com.finance.android.ui.theme.Disabled

@Preview
@Composable
fun AccountLikeItem() {

    val image = painterResource(id = R.drawable.ex_cp_logo)
    var isSelected = remember { mutableStateOf(false) }


    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = image,
            contentDescription = "cp logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(80.dp)
                .clip(CircleShape)

        )
        Spacer(modifier = Modifier.weight(0.2f))


        Column {
            Text(
                text = "신한은행정기적금"
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Row {
                Text(
                    text = "신한은행"
                )
                Text(
                    text = "1119183992"
                )

            }

        }
        Spacer(modifier = Modifier.weight(0.8f))

        IconButton(onClick = { isSelected.value = !isSelected.value }) {
            Icon(
                Icons.Filled.Star,
                contentDescription = "like",
                modifier = Modifier.size(40.dp),
                tint = if (isSelected.value) Color(0xffeeca66) else Disabled
            )

        }
    }
}

