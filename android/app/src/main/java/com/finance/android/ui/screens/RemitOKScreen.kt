package com.finance.android.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun RemitOKScreen() {
    val moneyValue = "1332342"
    Column() {
        Text(text = "${moneyValue}원")
        Text(text = "송금완료")
    }





}