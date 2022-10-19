package com.finance.android.ui.components

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.*
import com.finance.android.R
import com.finance.android.ui.theme.Disabled
import com.finance.android.utils.Const

class NavTab(
    @StringRes val name: Int,
    @RawRes val icon: Int,
    val screenName: String
) {
    companion object {
        val TABS: Array<NavTab> = arrayOf(
            NavTab(R.string.nav_home, R.raw.ic_nav_home, Const.HOME_SCREEN),
            NavTab(R.string.nav_product, R.raw.ic_nav_product, Const.PRODUCT_SCREEN),
            NavTab(R.string.nav_stock, R.raw.ic_nav_stock, Const.STOCK_SCREEN),
            NavTab(R.string.nav_more, R.raw.ic_nav_more, Const.MORE_SCREEN)
        )
    }
}

@Composable
fun BottomNavBar(
    navController: NavController,
    modifier: Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        NavTab.TABS.forEach { navTab ->
            val isSelected = currentRoute == navTab.screenName
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(navTab.icon))
            val progress by animateLottieCompositionAsState(
                composition,
                isPlaying = isSelected
            )
            val dynamicProperties = rememberLottieDynamicProperties(
                rememberLottieDynamicProperty(
                    property = LottieProperty.COLOR,
                    value = if (isSelected) MaterialTheme.colorScheme.primary.toArgb() else Disabled.toArgb(),
                    keyPath = arrayOf(
                        "**"
                    )
                ),
                rememberLottieDynamicProperty(
                    property = LottieProperty.STROKE_COLOR,
                    value = if (isSelected) MaterialTheme.colorScheme.primary.toArgb() else Disabled.toArgb(),
                    keyPath = arrayOf(
                        "**"
                    )
                )
            )

            BottomNavigationItem(
                icon = {
                    LottieAnimation(
                        composition,
                        progress = { if (isSelected) progress else 1f },
                        modifier = Modifier.size(24.dp),
                        dynamicProperties = dynamicProperties
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = navTab.name),
                        color = if (isSelected) MaterialTheme.colorScheme.primary else Disabled
                    )
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(navTab.screenName) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
