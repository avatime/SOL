package com.finance.android.ui.screens

import android.accessibilityservice.AccessibilityService.ScreenshotResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.domain.dto.response.UserProfileResponseDto
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.utils.Const
import com.finance.android.utils.Response
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.MyPageViewModel


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
            userInfo = (myPageViewModel.myInfo.value as Response.Success).data
        )
        is Response.Failure -> Loading("실패")
        else -> Loading()
    }
}

@Composable
fun Screen(
    navController: NavController,
    userInfo : UserProfileResponseDto
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Row(

        ){
            Image(
                painter = painterResource(R.drawable.paw),
                contentDescription = null, // 필수 param
            )
            Text(text = userInfo.username)
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
            modifier = Modifier.withBottomButton().background(Color.White),
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
