package com.finance.android.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.finance.android.ui.theme.Disabled

@Composable
fun CpListHeaderTab () {
    var selectedIndex by remember { mutableStateOf(0) }



    val list = listOf("은행", "증권")
    Row(modifier = Modifier.padding(end = 150.dp, bottom = 30.dp)) {
        TabRow(selectedTabIndex = selectedIndex,
            backgroundColor = Color.White,
            modifier = Modifier,


            ) {
            list.forEachIndexed { index, text ->
                val selected = selectedIndex == index
                Tab(

                    selected = selected,
                    onClick = { selectedIndex = index },
                    text = { Text(text = text) },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Disabled,


                    )
            }
        }




    }

}