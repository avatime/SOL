package com.finance.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
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

@Preview
@Composable
fun AccountListItem(onClickRemit: () -> Unit = {}) {
    PreviewAccountListItem_Remit(onClickRemit = onClickRemit)
}

@Composable
private fun Draw(
    modifier: Modifier,
    accountNumber: String,
    balance: Int,
    accountName: String,
    companyName: String,
    companyLogoPath: String,
    acMain: Int,
    trailing: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(R.dimen.padding_small),
                bottom = dimensionResource(R.dimen.padding_small)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box (modifier = Modifier.size(width = 40.dp, height = 53.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(companyLogoPath)
                        .crossfade(true)
                        .build(),
                    contentDescription = "accImage",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(40.dp)
                        .clip(CircleShape)
                )
                if (acMain == 1) {
                    Image(
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.TopCenter),
                        painter = painterResource(R.drawable.crown),
                        contentDescription = null
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(start = 12.dp, end = 6.dp)
            ) {
                Text(text = accountName, maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 14.sp)
                Text(text = DecimalFormat("#,###원").format(balance), fontWeight = FontWeight.Bold)
                Text(text = formatAccount(companyName = companyName, accountNumber = accountNumber), maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 12.sp, color = Color(R.color.noActiveColor))
            }
        }
        trailing?.invoke()
    }
}

@Composable
fun AccountListItem_Normal(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    accountNumber: String,
    balance: Int,
    accountName: String,
    companyLogoPath: String,
    companyName: String? = "",
    acMain: Int
) {
    if (companyName != null) {
        Draw(
            modifier = modifier
                .padding(contentPadding),
            accountNumber = accountNumber,
            balance = balance,
            accountName = accountName,
            companyLogoPath = companyLogoPath,
            companyName = companyName,
            acMain = acMain
        )
    }
}

@Composable
fun AccountListItem_Check(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    accountNumber: String,
    balance: Int,
    accountName: String,
    companyLogoPath: String,
    companyName: String? = "",
    checked: Boolean,
    acMain: Int,
    onClickItem: () -> Unit
) {
    if (companyName != null) {
        Draw(
            modifier = modifier
                .clip(RoundedCornerShape(20.dp))
                .clickable { onClickItem() }
                .padding(contentPadding),
            accountNumber = accountNumber,
            balance = balance,
            accountName = accountName,
            companyLogoPath = companyLogoPath,
            companyName = companyName,
            acMain = acMain,
            trailing = {
                Icon(
                    Icons.Filled.CheckCircle,
                    contentDescription = "checked",
                    tint = if (checked) MaterialTheme.colorScheme.primary else Disabled
                )
            }
        )
    }
}

@Composable
fun AccountListItem_Remit(
    modifier: Modifier = Modifier,
    accountNumber: String,
    balance: Int,
    accountName: String,
    companyLogoPath: String,
    companyName: String,
    acMain: Int,
    onClickItem: () -> Unit,
    onClickRemit: () -> Unit
) {
    Draw(
        modifier = modifier.clip(RoundedCornerShape(10.dp))
            .clickable { onClickItem() },
        accountNumber = accountNumber,
        balance = balance,
        accountName = accountName,
        companyLogoPath = companyLogoPath,
        companyName = companyName,
        acMain = acMain,
        trailing = {
            TextButton(
                onClick = { onClickRemit() },
                text = "송금",
                buttonType = ButtonType.ROUNDED,
                fontSize = dimensionResource(id = R.dimen.font_size_btn_small_text).value.sp
            )
        }
    )
}

@Composable
fun formatAccount(companyName: String, accountNumber: String) : String {
    val formatNum = StringBuffer(accountNumber)
    when(companyName) {
        "신한" -> return if(accountNumber.length == 11) formatNum.insert(5, "-").insert(3, "-").toString() else formatNum.insert(6, "-").insert(3, "-").toString()
        "기업" -> return formatNum.insert(11, "-").insert(9, "-").insert(3,"-").toString()
        "NH농협" -> return formatNum.insert(11, "-").insert(7, "-").insert(3, "-").toString()
        "하나" -> return formatNum.insert(9, "-").insert(3, "-").toString()
        "우리" -> return formatNum.insert(7, "-").insert(4, "-").toString()
        "국민" -> return if(accountNumber.length == 12) formatNum.insert(9, "-").insert(5, "-").insert(3, "-").toString() else formatNum.insert(8, "-").insert(6, "-").toString()
        "우체국" -> return formatNum.insert(8, "-").insert(6, "-").toString()
        "카카오뱅크" -> return formatNum.insert(6, "-").insert(4, "-").toString()
        "케이뱅크" -> return formatNum.insert(6, "-").insert(3, "-").toString()
        "토스뱅크" -> return formatNum.insert(4, "-").toString()
        "새마을금고" -> return formatNum.insert(6, "-").insert(4, "-").toString()
        "씨티" -> return formatNum.insert(9, "-").insert(3, "-").toString()
        "광주" -> return formatNum.insert(6, "-").insert(3, "-").toString()
        "대구" -> return formatNum.insert(11, "-").insert(5, "-").insert(3,"-").toString()
    }
    return if(accountNumber.length > 10) formatNum.insert(9, "-").insert(3, "-").toString() else accountNumber
}

@Composable
fun AccountListItem_Select(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    accountNumber: String,
    balance: Int,
    accountName: String,
    companyLogoPath: String,
    companyName: String? = "",
    selected: Boolean,
    acMain: Int,
    onClickItem: () -> Unit
) {
    if (companyName != null) {
        Draw(
            modifier = modifier
                .clip(RoundedCornerShape(20.dp))
                .border(
                    width = 1.dp,
                    color = if (selected) MaterialTheme.colorScheme.primary else Disabled,
                    shape = RoundedCornerShape(20.dp)
                )
                .clickable { onClickItem() }
                .padding(contentPadding),
            accountNumber = accountNumber,
            balance = balance,
            accountName = accountName,
            companyLogoPath = companyLogoPath,
            companyName = companyName,
            acMain = acMain
        )
    }
}

@Composable
fun AccountListItem_Arrow(
    modifier: Modifier = Modifier,
    accountNumber: String,
    balance: Int,
    accountName: String,
    companyLogoPath: String,
    companyName: String? = "",
    acMain: Int,
    onClickItem: () -> Unit
) {
    if (companyName != null) {
        Draw(
            modifier = modifier
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    onClickItem()
                },
            accountNumber = accountNumber,
            balance = balance,
            accountName = accountName,
            companyLogoPath = companyLogoPath,
            companyName = companyName,
            acMain = acMain,
            trailing = {
                Icon(
                    painter = painterResource(R.drawable.arrow_forward_ios),
                    contentDescription = "forwardArrow"
                )
            }
        )
    }
}

@Preview
@Composable
private fun PreviewAccountListItem_Check() {
    AccountListItem_Check(
        modifier = Modifier,
        accountNumber = "accountNumber",
        balance = 10000,
        accountName = "accountName",
        companyLogoPath = "companyLogoPath",
        checked = true,
        acMain = 1,
        onClickItem = {}
    )
}

@Preview
@Composable
private fun PreviewAccountListItem_Remit(onClickRemit: () -> Unit = {}) {
    AccountListItem_Remit(
        modifier = Modifier,
        accountNumber = "accountNumber",
        balance = 10000,
        accountName = "accountName",
        companyLogoPath = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png",
        companyName = "신한",
        acMain = 1,
        onClickItem = {},
        onClickRemit = onClickRemit
    )
}

@Preview
@Composable
private fun PreviewAccountListItem_Select() {
    AccountListItem_Select(
        accountNumber = "accountNumber",
        balance = 10,
        accountName = "accountName",
        companyLogoPath = "123",
        selected = true,
        acMain = 1,
        onClickItem = {}
    )
}

@Preview
@Composable
private fun PreviewAccountListItem_Arrow() {
    AccountListItem_Arrow(
        accountNumber = "accountNumber",
        balance = 10000,
        accountName = "accountName",
        companyLogoPath = "123",
        acMain = 1,
        onClickItem = {}
    )
}
