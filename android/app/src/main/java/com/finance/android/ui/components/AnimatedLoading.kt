package com.finance.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.*
import com.finance.android.R

@Composable
fun AnimatedLoading(
    modifier: Modifier = Modifier,
    text: String? = null
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        val (center) = createRefs()
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ic_loading))
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever
        )

        Column(
            modifier = modifier.constrainAs(center) {
                centerHorizontallyTo(parent)
                centerVerticallyTo(parent)
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                modifier = modifier
                    .size(160.dp),
                composition = composition,
                progress = { progress },
            )
            Spacer(modifier = modifier.height(5.dp))
            text?.let {
                Text(
                    text = text,
                    fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp,
                    lineHeight = dimensionResource(id = R.dimen.font_size_medium).value.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
