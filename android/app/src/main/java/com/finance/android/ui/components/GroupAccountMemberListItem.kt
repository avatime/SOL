package com.finance.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

@Composable
fun GroupAccountMemberListItem(img: String, name: String, type: String?) {
    Row(modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.CenterVertically) {
        if (type == "") {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(img)
                    .crossfade(true)
                    .build(),
                contentDescription = "연락처 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Column(verticalArrangement = Arrangement.Center) {
                Text(text = name, fontSize = 16.sp, modifier = Modifier.padding(bottom = 1.dp))
                Text(text = "")
            }
        } else if (type == "관리자") {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(img)
                    .crossfade(true)
                    .build(),
                contentDescription = "연락처 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Column(verticalArrangement = Arrangement.Center) {
                Text(text = name, fontSize = 16.sp, modifier = Modifier.padding(bottom = 1.dp))
                Text(text = "관리자", color = Color(R.color.noActiveColor))
            }
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_contact_avatar),
                contentDescription = "",
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Row() {
                Column(verticalArrangement = Arrangement.Center) {
                    Text(text = name, fontSize = 16.sp, modifier = Modifier.padding(bottom = 1.dp))
                    Text(text = "비회원", color = Color(R.color.noActiveColor))
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = { /*TODO*/ }, text = "초대", buttonType = ButtonType.ROUNDED)


            }
        }

    }
}