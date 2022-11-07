package com.finance.android.ui.screens

import android.util.DisplayMetrics
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R
import com.finance.android.domain.dto.response.DailyProfileResponseDto
import com.finance.android.domain.dto.response.UserProfileResponseDto
import com.finance.android.ui.components.showProfileList
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.utils.ext.toPx
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.MyPageViewModel
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import com.holix.android.bottomsheetdialog.compose.NavigationBarProperties


@Composable
fun MoreScreen(
    navController: NavController,
    myPageViewModel: MyPageViewModel = hiltViewModel()
) {
//    val context = LocalContext.current
//    var name by remember { mutableStateOf("") }
//    LaunchedEffect(Unit){
//        UserStore(context).getValue(UserStore.KEY_USER_NAME).collect {
//            name = it
//        }
//    }
    LaunchedEffect(Unit) {
        myPageViewModel.launchMyPage()
    }
    when(myPageViewModel.getLoadState()) {
        is Response.Success -> Screen(
            navController = navController,
            userInfo = (myPageViewModel.myInfo.value as Response.Success).data,
            profileList = (myPageViewModel.profileList.value as Response.Success).data
        )
        is Response.Failure -> Loading("실패")
        else -> Loading()
    }
}

@Composable
fun Screen(
    navController: NavController,
    userInfo : UserProfileResponseDto,
    profileList : MutableList<DailyProfileResponseDto>
) {
    val context = LocalContext.current
    var showProfileList by remember { mutableStateOf(false) }

    if(showProfileList) {
        val outMetrics = DisplayMetrics()

        BoxWithConstraints {
            BottomSheetDialog(
                onDismissRequest = {
                    showProfileList = false
                },
                properties = BottomSheetDialogProperties(
                    navigationBarProperties = NavigationBarProperties(),
                    behaviorProperties = BottomSheetBehaviorProperties(
                        maxHeight = BottomSheetBehaviorProperties.Size(this@BoxWithConstraints.maxHeight.toPx(context)/2),
                    )
                )
            ) {
                Surface (
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(text = "프로필 이미지", style = MaterialTheme.typography.headlineMedium)
                        showProfileList(profileList)
                    }
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.calendar_default))
        ){
            Button(onClick = {showProfileList = !showProfileList}) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(userInfo.profileUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = userInfo.profileName,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = dimensionResource(R.dimen.padding_small))
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = userInfo.username)
            }
        }

        Button(
            onClick = {
                navController.navigate(Const.Routes.ATTENDANCE)
            },
            modifier = Modifier.withBottomButton()
        ) {
            Text("포인트")
            Text(text = userInfo.point.toString() + "포인트")
        }

        MenuList(navController = navController)
    }
}

@Composable
private fun MenuList(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.calendar_default))
            .background(
                MaterialTheme.colorScheme.surface,
                RoundedCornerShape(dimensionResource(R.dimen.calendar_default))
            )
    ){
        Button(
            onClick = {
                navController.navigate(Const.Routes.ATTENDANCE)
            },
            modifier = Modifier
                .withBottomButton()
                .background(Color.White),
        ) {
            Image(
                painter = painterResource(R.drawable.ssal),
                contentDescription = null, // 필수 param
            )
            Text("출석체크")
        }

        Button(
            onClick = {
                navController.navigate(Const.Routes.WALK)
            },
            modifier = Modifier.withBottomButton()
        ) {
            Text("만보기")
        }

        Button(
            onClick = {
                navController.navigate(Const.Routes.ATTENDANCE)
            },
            modifier = Modifier.withBottomButton()
        ) {
            Text("모두의 통장")
        }
    }
}

@Composable
private fun Loading(
    message : String = "로딩 중...",
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = PaddingValues().calculateTopPadding())
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.background)
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Text(text = message, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
    }
}
