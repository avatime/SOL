package com.finance.android.ui.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
    cdNo: String,
    cdImgPath: String,
    cdName: String,
) {
    val current = LocalDateTime.now()
    val formattedM = current.format(DateTimeFormatter.ofPattern("M"))
    val formattedY = current.format(DateTimeFormatter.ofPattern(("YYYY")))
    val month = formattedM.toInt()
    val year = formattedY.toInt()

    fun launch() {
        cardViewModel.loadCardBill(cdNo, month, year)
        cardViewModel.loadCardBenefit(cdNo)
    }

    LaunchedEffect(Unit) {
        launch()
    }

    Scaffold(
        topBar = {
            BackHeaderBar(text = "내 카드", modifier = Modifier, onClickBack = onClose)
        }
    ) { innerPaddingModifier ->
//        when (val data = cardViewModel.getLoadCardBillandBenefit()) {
//            is Response.Success -> {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPaddingModifier),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(cdImgPath)
                            .crossfade(true)
                            .build(),
                        contentDescription = "카드 사진",
                        modifier = Modifier
                            .size(300.dp)
                            .padding(vertical = 10.dp)

                    )
                    Text(text = cdName, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
            }
//            is Response.Loading -> {}
//            else -> {}
//        }
//
//    }


}