package com.finance.android.ui.fragments

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finance.android.ui.screens.addasset.AddAssetIntroScreen
import com.finance.android.ui.screens.addasset.AddAssetRepresentScreen
import com.finance.android.ui.screens.addasset.AddAssetResultScreen
import com.finance.android.ui.screens.addasset.AddAssetSelectScreen
import com.finance.android.utils.Const
import com.finance.android.viewmodels.AddAssetViewModel

@Composable
fun AddAssetFragment(
    modifier: Modifier = Modifier,
    addAssetViewModel: AddAssetViewModel = hiltViewModel(),
    onClose: () -> Unit
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Const.ADD_ASSET_INTRO_SCREEN
    ) {
        composable(Const.ADD_ASSET_INTRO_SCREEN) {
            AddAssetIntroScreen(
                modifier = modifier,
                onClickBack = onClose,
                onClickNext = {
                    navController.navigate(Const.ADD_ASSET_SELECT_SCREEN) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Const.ADD_ASSET_SELECT_SCREEN) {
            AddAssetSelectScreen(
                addAssetViewModel = addAssetViewModel,
                onClickBack = {
                    navController.popBackStack()
                },
                onClickNext = {
                    navController.navigate(Const.ADD_ASSET_REP_SCREEN) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Const.ADD_ASSET_REP_SCREEN) {
            AddAssetRepresentScreen(navController = navController)
        }
        composable(Const.ADD_ASSET_RESULT_SCREEN) {
            AddAssetResultScreen(navController = navController)
        }
    }
}
