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
import androidx.compose.ui.unit.dp
import com.finance.android.R
import com.finance.android.ui.theme.Disabled

@Composable
fun AccountLikeItem() {

    val image = painterResource(id = R.drawable.ex_cp_logo)
    var isSelected = remember { mutableStateOf(false) }


    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {

        Row(modifier = Modifier.fillMaxHeight()) {
            Image(
                painter = image,
                contentDescription = "cp logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(80.dp)
                    .clip(CircleShape)

            )
            Spacer(modifier = Modifier.padding(10.dp))


            Column(
               modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "신한은행정기적금"
                )
                Row {
                    Text(
                        text = "신한은행"
                    )
                    Text(
                        text = "1119183992"
                    )

                }

            }
            Spacer(modifier = Modifier.padding(10.dp))

            IconButton(onClick = { isSelected.value = !isSelected.value }) {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = "like",
                    tint = if (isSelected.value) Color(0xffeeca66) else Disabled
                )

            }


        }

    }


}

