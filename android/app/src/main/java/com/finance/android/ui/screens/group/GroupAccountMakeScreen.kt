package com.finance.android.ui.screens.group

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun showGroupAccountIntro(){
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ){

                Text(text = "모두의 통장", style = MaterialTheme.typography.)
                Text(text = "가족, 친구, 연인과 함께 돈 같이 모으고 함께 써요")

        }
}