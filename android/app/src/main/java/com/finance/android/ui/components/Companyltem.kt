package com.finance.android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.domain.dto.response.BankInfoResponseDto
import com.finance.android.viewmodels.RemitViewModel

@Composable
fun CompanyItem(
    cpName: String, cpLogo: String, remitViewModel: RemitViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                remitViewModel.onClickReceiveBank(BankInfoResponseDto(cpName,cpLogo))
            }
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(cpLogo)
                .crossfade(true)
                .build(), contentDescription = "회사 로고", contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(text = "${cpName}은행")
    }
}

//@Preview
//@Composable
//fun PreviewCompanyItem() {
//    CompanyItem(
//
//    )
//
//}
//
//fun CompanyItem(cpName: String, cpLogo: String) {
//    cpName = "우리",
//    cpLogo = "https://w.namu.la/s/8e3ea802358c827ac96c128113c80ecb4529d429f9902973d5adb2d72b95bacf5888b8939beefff4ff0e78da8a0ed6aeb13d04b869c508f7cd4bbc51567b0da8ff1df5048908ecf179522e185514ba4d47afa717c57c3e039968f7c87aa2c7e5",
//
//
//}
