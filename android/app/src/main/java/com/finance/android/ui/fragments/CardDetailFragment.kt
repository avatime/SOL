package com.finance.android.ui.fragments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finance.android.domain.dto.response.CardBenefitInfoResponseDto
import com.finance.android.ui.components.BackHeaderBar
import com.finance.android.utils.Response
import com.finance.android.viewmodels.CardViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable

fun CardDetailFragment(
    navController: NavController,
    onClose: () -> Unit,
    cardViewModel: CardViewModel = hiltViewModel(),
    cardNo: String,
    cardImgPath: String,
    cardName: String,
    benefitList: MutableList<CardBenefitInfoResponseDto>
) {
    val current = LocalDateTime.now()
    val formattedM = current.format(DateTimeFormatter.ofPattern("M"))
    val formattedY = current.format(DateTimeFormatter.ofPattern(("YYYY")))
    val month = formattedM.toInt()
    val year = formattedY.toInt()

    fun launch() {
        cardViewModel.loadCardBill(cardNo, month, year)
    }

    LaunchedEffect(Unit) {
        launch()
    }

    Scaffold(
        topBar = {
            BackHeaderBar(text = "내 카드", modifier = Modifier, onClickBack = onClose)
        }
    ) { innerPaddingModifier ->
        when (val data = cardViewModel.getLoadCardBill()) {
            is Response.Success -> {
                Column(modifier = Modifier.padding(innerPaddingModifier)) {

                }
            }
            is Response.Loading -> {}
            else -> {}
        }

    }


}