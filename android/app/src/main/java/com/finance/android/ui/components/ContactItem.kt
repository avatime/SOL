package com.finance.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R

@Composable
fun ContactItem(name: String, number: String, avatar: String, modifier: Modifier) {

    Button (onClick = {}, colors = ButtonDefaults.buttonColors(Color.White)){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .height(75.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (avatar == "null") {
                Image(
                    painter = painterResource(id = R.drawable.ic_contact_avatar),
                    contentDescription = "",
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                )
            } else {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(avatar)
                        .crossfade(true)
                        .build(),
                    contentDescription = "연락처 이미지",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.padding(15.dp))


            Column() {
                Text(text = name, fontSize = dimensionResource(R.dimen.account_like_name).value.sp)
                Spacer(modifier = Modifier.padding(3.dp))
                Text(
                    text = number,
                    fontSize = dimensionResource(R.dimen.account_like_account_number).value.sp,
                    color = Color(R.color.noActiveColor)
                )
                Spacer(modifier = Modifier.fillMaxWidth())

            }


        }

    }


}

@Preview
@Composable
fun contact () {
    ContactItem(name = "채윤선", number = "010-4901-6695", avatar = "null", modifier = Modifier)
}