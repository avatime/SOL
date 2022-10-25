package com.finance.android.ui.fragments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun ContactScreen() {

   Column(horizontalAlignment = Alignment.CenterHorizontally) {
       Text(text = "연락처를 불러올까요?", textAlign = TextAlign.Center, )
       Button(onClick = { /*TODO*/ }) {
           
       }
       
   }
}

@Composable
fun AccessContact() {

}

