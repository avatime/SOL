package com.finance.android.ui.screens.groupAccount

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*


import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin

import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.finance.android.R
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.ui.components.TextInput
import com.finance.android.ui.theme.Typography
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.GroupAccountViewModel

import com.finance.android.utils.Const


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GroupAccountMakeScreen(
    navController: NavController,
    groupAccountViewModel: GroupAccountViewModel,
    modifier: Modifier
) {
    var visible by remember { mutableStateOf(true) }

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
            // Set the start width to 20 (pixels), 0 by default
            enter = slideInVertically(
                // Start the slide from 40 (pixels) above where the content is supposed to go, to
                // produce a parallax effect
                initialOffsetY = { -1234557764 }
            ) + expandVertically(
                expandFrom = Alignment.Top
            ) + scaleIn(
                // Animate scale from 0f to 1f using the top center as the pivot point.
                transformOrigin = TransformOrigin(1f, 0f)
            ) + fadeIn(initialAlpha = 1f),
        ) {
            Text(text = "모두의 통장", style = Typography.headlineLarge)
        }
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.font_size_title_desc)))
        Text(text = "가족, 친구, 연인과 함께", fontSize = 20.sp, color = Color(R.color.noActiveColor))
        Text(text = "돈 같이 모으고 함께 써요", fontSize = 20.sp, color = Color(R.color.noActiveColor))
        Spacer(modifier = Modifier.size(30.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_groupintro),
            contentDescription = "GroupAccount Intro"
        )
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.font_size_title_desc)))
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = { navController.navigate(Const.GROUP_ACCOUNT_NAME_SCREEN) },
            modifier = Modifier
                .withBottomButton(),
            text = "5초만에 만들기",
            buttonType = ButtonType.ROUNDED,
        )
    }

}






