package com.finance.android.ui.screens.groupAccount


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.utils.Const
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.GroupAccountViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import com.finance.android.R
import java.util.*

@Composable
fun DuesDataPickScreen(
    navController: NavController,
    modifier: Modifier,
    groupAccountViewModel: GroupAccountViewModel
) {

    val isNextTo = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val dialogState = rememberMaterialDialogState()
        LaunchedEffect(Unit) {
            dialogState.show()
            isNextTo.value = false
        }
        // Declaring a string value to
        // store date in string format
        val mDate = remember { mutableStateOf("") }
        Spacer(modifier = Modifier.weight(0.5f))
        Column(
        ) {
            // Declaring DatePickerDialog and setting
            // initial values as current values (present year, month and day)

            MaterialDialog(
                dialogState = dialogState,
                buttons = {
                    positiveButton("확인", onClick = { isNextTo.value = true })
                    negativeButton("취소")
                },
            ) {
                datepicker(
                    colors = DatePickerDefaults.colors(Color.White),
                    yearRange = IntRange(2022, 2100),
                    allowedDateValidator = {
                        it.isAfter(LocalDate.now().minusDays(1))
                    }
                ) { date ->
                    mDate.value = "${date.year}-${date.monthValue}-${date.dayOfMonth}"
                    // Do stuff with java.time.LocalDate object which is passed in
                }
            }

            IconButton(onClick = {
                dialogState.show()
            }) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_group_calendar),
                        contentDescription = "", modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                    )
                    Spacer(modifier = Modifier.padding(25.dp))
                    Text(
                        text = "${mDate.value}까지",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(0.5f))

        if (isNextTo.value) {
            TextButton(
                onClick = {
                    navController.navigate(Const.DUES_MEMBER_LIST)
                    groupAccountViewModel.mDate.value = mDate.value
                },
                text = "다음",
                buttonType = ButtonType.ROUNDED,
                modifier = Modifier.withBottomButton()
            )
        }
    }


}







