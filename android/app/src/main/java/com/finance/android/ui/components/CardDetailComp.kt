package com.finance.android.ui.components

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.utils.Const
import java.text.DecimalFormat
import java.time.YearMonth

@Composable
fun CardDetailComp(
    modifier: Modifier,
    cdName: String, // 카드 이름
    cdImgPath: String, // 카드 이미지
    cdNo: String, // 카드 번호
    balance: Int, // 월 청구 금액
    navController : NavController
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }

    Column(
        modifier = modifier
            .padding(18.dp)
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = slideInHorizontally(
                initialOffsetX = { -800 }, // small slide 300px
                animationSpec = tween(
                    durationMillis = 200,
                    easing = LinearEasing // interpolator
                )
            )
        ) {
            Column {
                Text(
                    text = cdName,
                    fontSize = 17.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = cdNo,
                    fontSize = 12.sp
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = slideInHorizontally(
                    initialOffsetX = { -800 }, // small slide 300px
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = LinearEasing // interpolator
                    )
                )
            ) {
                Column {
                    Text(
                        text = "${YearMonth.now().monthValue}월 청구 금액",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = DecimalFormat("#,###원").format(balance),
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1.0f))
            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically(
                    initialOffsetY = { 800 }, // small slide 300px
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = LinearEasing // interpolator
                    )
                )
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(cdImgPath)
                        .crossfade(true)
                        .build(),
                    contentDescription = "cardImage",
                    modifier = modifier
                        .clickable {
                            val pathTmp = Uri.encode(cdImgPath)
                            val cardPdCode  = 1
                            navController.navigate("${Const.Routes.CARD_BENEFIT}/${cardPdCode}/$pathTmp/${cdName}")
                        }
                        .size(130.dp)
                )
            }
        }
    }
}
