package com.finance.android.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.R
import com.finance.android.domain.dto.response.CardRecommendResponseDto
import com.finance.android.ui.screens.CardProductScreen
import com.finance.android.ui.screens.asset.AssetLifeScreen
import com.finance.android.ui.screens.asset.AssetStockScreen
import com.finance.android.ui.screens.asset.*
import com.finance.android.ui.theme.Disabled
import com.finance.android.utils.Const

// import com.google.accompanist.pager.*

@Composable
fun HeaderProductTabBar(
    modifier: Modifier,
    navController: NavController,
    cardRecommendList: CardRecommendResponseDto
) {
    var selectedIndex by remember { mutableStateOf(0) }

    val list = listOf("카드", "보험")
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium).value.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            try {
                Class
                    .forName("androidx.compose.material3.TabRowKt")
                    .getDeclaredField("ScrollableTabRowMinimumTabWidth")
                    .apply {
                        isAccessible = true
                    }
                    .set(this, 0f)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            ScrollableTabRow(
                modifier = Modifier
                    .fillMaxWidth(),
                selectedTabIndex = selectedIndex,
                containerColor = MaterialTheme.colorScheme.background,
                indicator = {},
                divider = {},
                edgePadding = 0.dp
            ) {
                list.forEachIndexed { index, text ->
                    val selected = selectedIndex == index
                    Tab(
                        modifier = Modifier
                            .padding(5.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        selected = selected,
                        onClick = { selectedIndex = index },
                        text = {
                            Text(
                                text = text,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        selectedContentColor = MaterialTheme.colorScheme.onSurface,
                        unselectedContentColor = Disabled
                    )
                }
            }
        }

        when (selectedIndex) {
            0 -> {
                CardProductScreen(navController = navController, cardRecommendList = cardRecommendList)
            }
            1 -> {
                AssetCardScreen(navController)
            }
        }
    }
}
