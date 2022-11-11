package com.finance.android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R
import com.finance.android.ui.theme.Disabled
import java.text.DecimalFormat

@Composable
fun InsuranceListItem() {
    PreviewInsuranceListItem_Arrow()
}

@Composable
private fun Draw(
    modifier: Modifier,
    insuranceName: String,
    fee: Int,
    myName: String,
    isName: String,
    trailing: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(R.dimen.padding_medium),
                bottom = dimensionResource(R.dimen.padding_small)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png")
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
        ) {
            Text(text = insuranceName, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = "보험료: ${DecimalFormat("#,###원").format(fee)}", fontWeight = FontWeight.Bold)
            Text(text = "계약자: ${myName}|피보험자: $isName", fontSize = 10.sp)
        }
        Spacer(modifier = Modifier.weight(1.0f))
        trailing?.invoke()
    }
}

@Composable
fun InsuranceListItem_Arrow(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    insuranceName: String,
    fee: Int,
    myName: String,
    isName: String,
    onClickItem: () -> Unit
) {
    Draw(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClickItem() }
            .padding(contentPadding),
        insuranceName = insuranceName,
        fee = fee,
        myName = myName,
        isName = isName,
        trailing = {
            Icon(
                painter = painterResource(R.drawable.arrow_forward_ios),
                contentDescription = "forwardArrow"
            )
        }
    )
}

@Composable
fun InsuranceListItem_Check(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    insuranceName: String,
    fee: Int,
    myName: String,
    isName: String,
    checked: Boolean,
    onClickItem: () -> Unit
) {
    Draw(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClickItem() }
            .padding(contentPadding),
        insuranceName = insuranceName,
        fee = fee,
        myName = myName,
        isName = isName,
        trailing = {
            Icon(
                Icons.Filled.CheckCircle,
                contentDescription = "check",
                tint = if (checked) MaterialTheme.colorScheme.primary else Disabled
            )
        }
    )
}

@Composable
fun InsuranceListItem_Normal(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    insuranceName: String,
    fee: Int,
    myName: String,
    isName: String,
    onClickItem: () -> Unit
) {
    Draw(
        modifier = modifier
            .padding(contentPadding)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClickItem() },
        insuranceName = insuranceName,
        fee = fee,
        myName = myName,
        isName = isName,
    )
}

@Preview
@Composable
private fun PreviewInsuranceListItem_Arrow() {
    InsuranceListItem_Arrow(
        modifier = Modifier,
        insuranceName = "신종단체상해보험",
        fee = 10000,
        myName = "계약자",
        isName ="피보험자",
        onClickItem = {}
    )
}

@Preview
@Composable
private fun PreviewInsuranceListItem_Check() {
    InsuranceListItem_Check(
        modifier = Modifier,
        insuranceName = "신종단체상해보험",
        fee = 10000,
        myName = "계약자",
        isName ="피보험자",
        checked = true,
        onClickItem = {}
    )
}
