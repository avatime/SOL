package com.finance.android.ui.screens.remit

import android.util.DisplayMetrics
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R
import com.finance.android.ui.components.*
import com.finance.android.ui.theme.Disabled
import com.finance.android.utils.Const
import com.finance.android.utils.ext.toPx
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.RemitViewModel
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import com.holix.android.bottomsheetdialog.compose.NavigationBarProperties
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(remitViewModel: RemitViewModel, navController: NavController) {
    remitViewModel.requestRemit.value = false
    val context = LocalContext.current
    val accountNumber = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DisplayMetrics()
        BoxWithConstraints {
            BottomSheetDialog(
                onDismissRequest = {
                    showDialog = false
                },
                properties = BottomSheetDialogProperties(
                    navigationBarProperties = NavigationBarProperties(),
                    behaviorProperties = BottomSheetBehaviorProperties(
                        maxHeight = BottomSheetBehaviorProperties.Size(
                            (this@BoxWithConstraints.maxHeight.toPx(
                                context
                            ) * 0.8).toInt()
                        )
                    )
                )
            ) {
                Surface(
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                ) {
                    CpListSheet(
                        modifier = Modifier,
                        remitViewModel = remitViewModel,
                        onDismiss = {
                            showDialog = false
                        }
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
        // verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.padding(40.dp))
        OutlinedButton(
            onClick = {
                coroutineScope.launch {
                    showDialog = true
                }
            },
            modifier = Modifier
                .width(250.dp)
                .height(60.dp)
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = colorResource(R.color.noActiveColor),
                containerColor = Color.Transparent
            )

        ) {
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
                                .data(remitViewModel.selectedReceiveBank.value!!.cpLogo)
                                .crossfade(true)
                                .build(),
                            contentDescription = "회사 로고",
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(text = remitViewModel.selectedReceiveBank.value!!.cpName)
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
                containerColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
            ),
            placeholder = {
                Text(text = "계좌번호 입력", color = Disabled)
            }
        )
        if (accountNumber.value.isNotEmpty()) {
            TextButton(
                onClick = {
                    remitViewModel.checkRightAccount(
                        cpCode = remitViewModel.cpCode.value,
                        acNo = accountNumber.value,
                        onSuccess = {
                            navController.navigate(Const.INPUT_MONEY_SCREEN)
                        }
                    )
                },
                text = "다음",
                modifier = Modifier.withBottomButton(),
                buttonType = ButtonType.ROUNDED

            )
        }
        if (!remitViewModel.isRightAccount.value) {
            CustomDialog(
                dialogType = DialogType.ERROR,
                dialogActionType = DialogActionType.ONE_BUTTON,
                title = "계좌번호 오류",
                subTitle = "다시 한번 확인해주세요",
                onPositive = {
                    remitViewModel.isRightAccount.value = true
                }
            )
        }
    }
}
