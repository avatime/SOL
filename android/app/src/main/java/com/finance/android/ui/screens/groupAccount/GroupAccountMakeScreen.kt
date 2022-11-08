package com.finance.android.ui.screens.group

import android.content.res.Resources
import android.media.Image
import android.renderscript.ScriptGroup.Input
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.TextButton
import com.finance.android.ui.components.TextInput
import com.finance.android.ui.screens.login.InputUserInfoStep
import com.finance.android.ui.theme.Typography
import com.finance.android.utils.ext.withBottomButton
import com.finance.android.viewmodels.GroupAccountViewModel
import com.finance.android.viewmodels.MyPageViewModel
import androidx.compose.material.Scaffold
import com.finance.android.ui.fragments.GroupAccountFragment

@Composable
fun GroupAccountMakeScreen(
    navController: NavController,
    groupAccountViewModel: GroupAccountViewModel,
    innerNavController: NavController
){
    val pageId = remember { mutableStateOf(0) }
    if(pageId.value==1){
        InputGroupName(groupAccountViewModel = groupAccountViewModel)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(color = MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BackHeaderBar(text = "",modifier = Modifier)
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_large)))
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_large)))
        Text(text = "모두의 통장", style = Typography.headlineLarge)
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.font_size_title_desc)))
        Text(text = "가족, 친구, 연인과 함께", fontSize = 20.sp)
        Text(text = "돈 같이 모으고 함께 써요", fontSize = 20.sp)
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.font_size_small)))

        Image(
            painter = painterResource(id = R.drawable.ic_groupintro),
            contentDescription = "GroupAccount Intro"
        )
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.font_size_title_desc)))
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = { pageId.value = 1 },
            modifier = Modifier
                .withBottomButton(),

            text = "30초만에 시작하기",
            buttonType = ButtonType.ROUNDED,
        )

    }
}

@Composable
fun InputGroupName(
    groupAccountViewModel: GroupAccountViewModel
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(Color.Red),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ){
        Text(
            text = "모두의 통장 이름을",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "입력해주세용",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
    Spacer(modifier = Modifier.size(0.dp, 70.dp))
    TextInput(
        value = groupAccountViewModel.name.value,
        onValueChange = {
            if (it.length <= 20){
                groupAccountViewModel.name.value = it
            }
        },
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .fillMaxWidth()
            .padding(0.dp)
    )

}


