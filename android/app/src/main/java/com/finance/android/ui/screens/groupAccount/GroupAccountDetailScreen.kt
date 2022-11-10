package com.finance.android.ui.screens.groupAccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.GroupAccountHeaderTabBar
import com.finance.android.ui.components.TextButton
import com.finance.android.viewmodels.GroupAccountViewModel
import java.util.NavigableMap
import com.finance.android.ui.theme.Typography
import com.finance.android.utils.Const
import com.finance.android.utils.ext.withBottomButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupAccountDetailScreen(
    navController: NavController,
    groupAccountViewModel: GroupAccountViewModel,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = modifier.padding(32.dp)
        ) {
            Column() {
                Text(
                    text = "모두의 통장 이름",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.size(dimensionResource(R.dimen.font_size_small)))
                Text(
                    text = "금액",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Scaffold(topBar = {
            GroupAccountHeaderTabBar(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface)
                ,
                navController = navController,
                groupAccountViewModel = groupAccountViewModel
            )
        })
        {contentPadding->
            Box(modifier = modifier.padding(top = contentPadding.calculateTopPadding())) {

            }
        }
    }


}