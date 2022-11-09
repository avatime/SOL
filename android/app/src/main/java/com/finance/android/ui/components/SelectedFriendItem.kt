package com.finance.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R

@Composable
fun SelectedFriendItem(img : String, name : String) {
    Column() {
        Column(horizontalAlignment = Alignment.End,) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Close, contentDescription ="" )
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (img == "null") {
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
            }
            Text(text=name)


        }


    }
}

@Preview
@Composable
fun PreviewSelectedItem() {
    SelectedFriendItem(img = "", name = "채윤선")
}