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
    cpCode : Int, cpName: String, cpLogo: String, remitViewModel: RemitViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp, bottom = 10.dp, start = 15.dp, end = 15.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                remitViewModel.onClickReceiveBank(BankInfoResponseDto(cpCode,cpName,cpLogo))
                remitViewModel.cpCode.value = cpCode
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(cpLogo)
                .crossfade(true)
                .build(), contentDescription = "회사 로고",
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(text = cpName, softWrap = true, maxLines = 1)
    }
}