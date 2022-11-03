package com.finance.android.ui.screens


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance.android.R
import com.finance.android.ui.components.CpListSheet
import com.finance.android.ui.components.TextInput
import com.finance.android.viewmodels.RemitViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountScreen(remitViewModel: RemitViewModel) {

    var accountNumber by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { BottomSheet(remitViewModel) },
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            //verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.padding(40.dp))
            OutlinedButton(
                onClick = {
                    coroutineScope.launch {
                        if (sheetState.isVisible) sheetState.hide()
                        else sheetState.show()
                    }
                },
                modifier = Modifier
                    .width(250.dp)
                    .height(60.dp)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colorResource(R.color.noActiveColor),
                    containerColor = Color.Transparent
                ),

                )

            {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "은행")
                    Spacer(modifier = Modifier.weight(1.0f))
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "selectBack")
                }
            }
//        Divider(color = MaterialTheme.colorScheme.onSurfaceVariant, thickness = 1f.dp                                                                                                                                  ,  modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp))
            TextInput(
                value = accountNumber,
                onValueChange = { accountNumber = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                label = "계좌번호"
            )
        }

    }
}

@Composable
fun BottomSheet(remitViewModel: RemitViewModel) {
    Column(


    ) {
        CpListSheet(modifier = Modifier, remitViewModel = remitViewModel)

    }
}


