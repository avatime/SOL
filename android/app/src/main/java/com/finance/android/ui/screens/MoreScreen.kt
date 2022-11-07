package com.finance.android.ui.screens

import android.util.DisplayMetrics
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import com.finance.android.ui.components.TransientSnackbar
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
    var showSnackbar by remember { mutableStateOf(false) }
    if (showSnackbar) {
        TransientSnackbar(
            onDismiss = { showSnackbar = false }
        ) {
            Text(
                text = "프로필 변경 완료!",
                color = MaterialTheme.colorScheme.surface
            )
        }
    }

    LaunchedEffect(Unit) {
        myPageViewModel.launchMyPage()
    }
    when(myPageViewModel.getLoadState()) {
        is Response.Success -> Screen(
            navController = navController,
            userInfo = (myPageViewModel.myInfo.value as Response.Success).data,
            profileList = (myPageViewModel.profileList.value as Response.Success).data,
            onClick = {
                myPageViewModel.callChangeProfile(it)
            }
        )
        is Response.Failure -> Loading("실패")
        else -> Loading()
    }
}

@Composable
fun Screen(
    navController: NavController,
    userInfo : UserProfileResponseDto,
    profileList : MutableList<DailyProfileResponseDto>,
    onClick : (profileId: Int) -> Unit = {}
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
                        showProfileList(profileList, onClickImage = {
                            onClick(it)
                            println("프로필 이미지 변경 $it")
//                            showSnackbar = true
                        })
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
                .padding(start = dimensionResource(R.dimen.calendar_default))
                .padding(end = dimensionResource(R.dimen.calendar_default))
                .padding(top = 35.dp)
                .padding(bottom = 13.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(userInfo.profileUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = userInfo.profileName,
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = dimensionResource(R.dimen.padding_small))
                    .clickable { showProfileList = !showProfileList }
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(text = userInfo.username, fontWeight = FontWeight.Bold, style = TextStyle(fontSize = 18.sp))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = dimensionResource(id = R.dimen.padding_medium))
                .padding(end = dimensionResource(id = R.dimen.padding_medium))
                .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
                .height(100.dp)
                .clickable { navController.navigate(Const.Routes.ATTENDANCE) }
                .background(
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(dimensionResource(R.dimen.calendar_default) / 2),
                ),
            verticalArrangement = Arrangement.Center
        ) {
            Column(modifier = Modifier.padding(start = 23.dp)) {
                Text(text = "포인트", fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
                Text(text = userInfo.point.toString() + "포인트", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        MenuList(navController = navController)
    }
}

@Composable
private fun MenuList(navController: NavController) {

    Column(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.account_like_account_number))
            .background(
                MaterialTheme.colorScheme.surface,
                RoundedCornerShape(dimensionResource(R.dimen.calendar_default))
            )
    ){
        Spacer(modifier = Modifier.size(10.dp))

        MoreMenuItem(
            onClickMenu = { navController.navigate(Const.Routes.ATTENDANCE) },
            painter = painterResource(R.drawable.ssal),
            text = "출석체크"
        )

        MoreMenuItem(
            onClickMenu = { navController.navigate(Const.Routes.WALK) },
            painter = painterResource(R.drawable.ssal),
            text = "만보기"
        )

        MoreMenuItem(
            onClickMenu = { navController.navigate(Const.Routes.ATTENDANCE) },
            painter = painterResource(R.drawable.ssal),
            text = "모두의 통장"
        )

        Spacer(modifier = Modifier.size(40.dp))
    }
}

@Composable
fun MoreMenuItem(
    onClickMenu: () -> Unit = {},
    painter: Painter,
    text : String
){
    Column(
//        modifier = Modifier.background(Color.Blue),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .withBottomButton()
                .clickable { onClickMenu() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painter,
                contentDescription = null, // 필수 param
            )
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.font_size_btn_small_text)))
            Text(text)
        }

        Divider(modifier = Modifier.fillMaxWidth(0.92f))
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
