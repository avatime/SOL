package com.finance.android.ui.fragments

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.finance.android.viewmodels.GroupAccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupFragment(navController: NavController, groupAccountViewModel: GroupAccountViewModel = hiltViewModel()) {
    val innerNavController  = rememberNavController()

}