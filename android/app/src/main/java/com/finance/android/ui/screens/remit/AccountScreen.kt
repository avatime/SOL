package com.finance.android.ui.screens


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.CompanyItem
import com.finance.android.ui.components.CpListSheet
import com.finance.android.ui.components.TextInput
import com.finance.android.utils.Const
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.RemitViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountScreen(remitViewModel: RemitViewModel, navController: NavController) {

    var accountNumber = remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.Expanded }
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (remitViewModel.selectedReceiveBank.value.toString() == "null") {
                        Text(text = "은행")
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(remitViewModel.selectedReceiveBank.value?.cpLogo)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "회사 로고",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(40.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            Text(text = "${remitViewModel.selectedReceiveBank.value?.cpName}은행")
                        }
                    }


                    Spacer(modifier = Modifier.weight(1.0f))
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "selectBack")
                }
            }
            TextField(
                value = accountNumber.value,
                onValueChange = { accountNumber.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colors.primary
                )
            )
            if (accountNumber.value.isNotEmpty()) {
                com.finance.android.ui.components.TextButton(
                    onClick = {
                        remitViewModel.checkRightAccount(
                            cpCode = 1,
                            acNo = accountNumber.value,
                            onSuccess = {
                                navController.navigate(Const.INPUT_MONEY_SCREEN)
                            })
                    },
                    text = "다음",
                    modifier = Modifier.withBottomButton(),
                    buttonType = ButtonType.ROUNDED,

                    )
            }


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


