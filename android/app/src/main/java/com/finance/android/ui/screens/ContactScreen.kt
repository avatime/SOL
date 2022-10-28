package com.finance.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.finance.android.R
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton

@Composable
@Preview
fun ContactScreen() {



    Column(

        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        //verticalArrangement = Arrangement.Center
    ) {

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ic_remit_contact))
        val progress by animateLottieCompositionAsState(composition)
        Spacer(modifier = Modifier.padding(40.dp))
        LottieAnimation(
            composition = composition,

            modifier = Modifier.size(100.dp),
            iterations = LottieConstants.IterateForever,
        )



        Spacer(modifier = Modifier.padding(20.dp))

        Text(text = "연락처를 불러올까요?")
        Spacer(modifier = Modifier.padding(10.dp))
        TextButton(
            onClick = { /*TODO*/ },
            text ="불러오기",
            buttonType = ButtonType.ROUNDED,
            modifier = Modifier.width(100.dp)
        )


    }
}



