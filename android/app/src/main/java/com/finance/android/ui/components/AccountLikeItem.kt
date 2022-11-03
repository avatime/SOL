package com.finance.android.ui.components


import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.TransformOrigin
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


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AccountLikeItem(
    bkStatus: Boolean,
    cpLogo: String,
    name: String,
    accountNumber: String,
    cpName: String
) {

    var isSelected = remember { mutableStateOf(bkStatus) }



    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(75.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(cpLogo)
                .crossfade(true)
                .build(),
            contentDescription = "회사 로고",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(70.dp)
                .height(70.dp)
                .clip(CircleShape)

        )
        Spacer(modifier = Modifier.weight(0.2f))


        Column {
            Text(
                text = name, fontSize = dimensionResource(R.dimen.account_like_name).value.sp,
            )
            Spacer(modifier = Modifier.padding(3.dp))
            Row {
                Text(
                    text = cpName,
                    fontSize = dimensionResource(R.dimen.account_like_account_number).value.sp,
                    color = Color(R.color.noActiveColor)

                )
                Spacer(modifier = Modifier.padding(1.dp))
                Text(
                    text = accountNumber,
                    fontSize = dimensionResource(R.dimen.account_like_account_number).value.sp,
                    color = Color(R.color.noActiveColor)
                )

            }

        }
        Spacer(modifier = Modifier.weight(0.8f))

        IconButton(onClick = { isSelected.value = !isSelected.value }) {


            AnimatedVisibility(
                visible = isSelected.value,
                enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
                exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
            ) {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = "like",
                    modifier = Modifier.size(35.dp),
                    tint = if (isSelected.value) Color(0xffeeca66) else Color.LightGray

                )


            }

            AnimatedVisibility(
                visible = !isSelected.value,
                enter = scaleIn(transformOrigin = TransformOrigin(0f, 0f)) +
                        fadeIn() + expandIn(expandFrom = Alignment.TopStart),
                exit = scaleOut(transformOrigin = TransformOrigin(0f, 0f)) +
                        fadeOut() + shrinkOut(shrinkTowards = Alignment.TopStart)
            ) {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = "like",
                    modifier = Modifier.size(35.dp),
                    tint = if (!isSelected.value) Color.LightGray else Color(0XFF736979)

                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AccountLikeItem(
        bkStatus = true,
        cpLogo = "it.cpLogo",
        name = "it.acReceive",
        accountNumber = "it.acNo",
        cpName = "it.cpName"
    )
}