package com.finance.android.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.finance.android.utils.Response
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.SampleViewModel

@Composable
fun StockScreen(
    sampleViewModel: SampleViewModel = hiltViewModel()
) {
    fun launch() {
        sampleViewModel.getData()
    }

    LaunchedEffect(Unit) {
        launch()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        when (val data = sampleViewModel.data.value) {
            is Response.Failure -> {
                Text(text = "Failure!!!")
            }
            is Response.Loading -> {
                Text(text = "Loading...")
            }
            is Response.Success -> {
                Text(text = "Success!!!")
                data.data!!.forEach {
                    Text(text = it.toString())
                }
            }
        }
        Spacer(modifier = Modifier.weight(1.0f))
        Button(
            onClick = { launch() },
            modifier = Modifier.withBottomButton()
        ) {
            Text("리로드")
        }
    }
}
