package com.finance.android.ui.screens.groupAccount

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.ui.theme.Typography
import com.finance.android.utils.Const
import com.finance.android.utils.ext.withBottomButton


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GroupAccountMakeScreen(
    navController: NavController,
    modifier: Modifier
) {
    val visible by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.padding(40.dp))
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(
                initialOffsetY = { -1234557764 }
            ) + expandVertically(
                expandFrom = Alignment.Top
            ) + scaleIn(
                transformOrigin = TransformOrigin(1f, 0f)
            ) + fadeIn(initialAlpha = 1f),
        ) {
            Text(text = "모두의 통장", style = Typography.headlineLarge)
        }
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.font_size_title_desc)))
        Text(text = "가족, 친구, 연인과 함께", fontSize = 20.sp, color = Color(R.color.noActiveColor))
        Text(text = "돈 같이 모으고 함께 써요", fontSize = 20.sp, color = Color(R.color.noActiveColor))
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "간편하게 송금 대표 계좌를 사용하여", fontSize = 20.sp, color = Color(R.color.noActiveColor))
        Text(text = "입출금을 해보아요", fontSize = 20.sp, color = Color(R.color.noActiveColor))

        Spacer(modifier = Modifier.weight(0.5f))
        Image(
            painter = painterResource(id = R.drawable.ic_groupintro),
            contentDescription = "GroupAccount Intro"
        )
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.font_size_title_desc)))
        Spacer(modifier = Modifier.weight(0.5f))
        TextButton(
            onClick = { navController.navigate(Const.GROUP_ACCOUNT_NAME_SCREEN) },
            modifier = Modifier
                .withBottomButton(),
            text = "5초만에 만들기",
            buttonType = ButtonType.ROUNDED,
        )
    }

}






