package com.finance.android.ui.screens.remit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.*
import com.finance.android.R
import com.finance.android.ui.components.ButtonType
import com.finance.android.utils.Const
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.RemitViewModel
import java.text.DecimalFormat

@Preview
@Composable
fun RemitOKScreen(
    sendMoney: Int = 10000,
    content: String = "content",
    onClose: () -> Unit = {}
) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ic_done))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )
    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.STROKE_COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "**"
            )
        )
    )
    Column(  modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(20.dp))
        LottieAnimation(
            composition,
            progress = { progress },
            modifier = Modifier.size(100.dp),
            dynamicProperties = dynamicProperties
        )
        Spacer(modifier = Modifier.padding(10.dp))

        Text(text = DecimalFormat("#,###원").format(sendMoney),  fontSize = 25.sp,)
        Text(text = content,  fontSize = 25.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.weight(1f))
        com.finance.android.ui.components.TextButton(
            onClick = onClose,
            text = "완료",
            modifier = Modifier.withBottomButton(),
            buttonType = ButtonType.ROUNDED,

            )
    }
}

