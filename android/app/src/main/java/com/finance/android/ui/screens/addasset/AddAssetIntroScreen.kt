package com.finance.android.ui.screens.addasset

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.finance.android.R
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.utils.ext.withBottomButton

@Composable
fun AddAssetIntroScreen(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onClickNext: () -> Unit
) {
    DrawScreen(
        modifier = modifier,
        onClickBack = onClickBack,
        onClickNext = onClickNext
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun DrawScreen(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit = {},
    onClickNext: () -> Unit = {}
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ic_coin))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            BackHeaderBar(
                text = stringResource(id = R.string.nav_add_asset),
                modifier = modifier,
                onClickBack = onClickBack,
                backgroundColor = MaterialTheme.colorScheme.surface
            )
        }
    ) {
        Column(
            modifier = modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.height(70.dp))
            Text(
                text = stringResource(id = R.string.msg_desc_add_asset),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.font_size_large).value.sp,
                    lineHeight = dimensionResource(id = R.dimen.font_size_large).value.sp
                )
            )
            Spacer(modifier = modifier.height(70.dp))
            LottieAnimation(
                composition,
                progress = { progress },
                modifier = Modifier.size(160.dp)
            )
            Spacer(modifier = modifier.weight(1f))
            TextButton(
                onClick = onClickNext,
                text = stringResource(id = R.string.btn_add_asset),
                buttonType = ButtonType.ROUNDED,
                modifier = Modifier.withBottomButton()
            )
        }
    }
}
