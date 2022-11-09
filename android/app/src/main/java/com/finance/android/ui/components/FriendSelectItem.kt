package com.finance.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.finance.android.ui.theme.Disabled

@Composable
fun FriendSelectItem(
//    modifier: Modifier,
    checked: Boolean,
    img: String,
    name: String,
    phone: String,
    onClickItem: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .height(75.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

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
        Spacer(modifier = Modifier.padding(15.dp))


        Column() {
            Text(text = name, fontSize = dimensionResource(R.dimen.account_like_name).value.sp)
            Spacer(modifier = Modifier.padding(3.dp))
            Text(
                text = phone,
                fontSize = dimensionResource(R.dimen.account_like_account_number).value.sp,
                color = Color(R.color.noActiveColor)
            )


        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { onClickItem() }) {
            Icon(
                Icons.Filled.CheckCircle,
                contentDescription = "check",
                tint = if (checked) MaterialTheme.colorScheme.primary else Disabled
            )

        }



    }

}

@Preview
@Composable
fun PreviwFriendSelectItem() {
    FriendSelectItem(checked = true, img = "", name = "채윤선", phone = "01049016695" ) {

    }
}