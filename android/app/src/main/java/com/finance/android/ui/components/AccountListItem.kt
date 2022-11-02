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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
fun AccountListItem() {
    PreviewAccountListItem_Remit()
}

@Composable
private fun Draw(
    modifier: Modifier,
    accountNumber: String?,
    balance: Int,
    accountName: String,
    companyLogoPath: String,
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
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(companyLogoPath)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
        ) {
            Text(text = accountName, fontWeight = FontWeight.Bold)
            Text(text = DecimalFormat("#,###원").format(balance))
            accountNumber?.let {
                Text(text = it)
            }
        }
        Spacer(modifier = Modifier.weight(1.0f))
        trailing?.invoke()
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
    checked: Boolean,
    onClickItem: () -> Unit
) {
    Draw(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClickItem() }
            .padding(contentPadding),
        accountNumber = accountNumber,
        balance = balance,
        accountName = accountName,
        companyLogoPath = companyLogoPath,
        trailing = {
            Icon(
                Icons.Filled.CheckCircle,
                contentDescription = stringResource(id = R.string.btn_all),
                tint = if (checked) MaterialTheme.colorScheme.primary else Disabled
            )
        }
    )
}

@Composable
fun AccountListItem_Remit(
    modifier: Modifier,
    accountNumber: String,
    balance: Int,
    accountName: String,
    companyLogoPath: String,
    onClickItem: () -> Unit,
    onClickRemit: () -> Unit
) {
    Draw(
        modifier = modifier.clickable {
            onClickItem()
        },
        accountNumber = accountNumber,
        balance = balance,
        accountName = accountName,
        companyLogoPath = companyLogoPath,
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
        onClickItem = {}
    )
}

@Preview
@Composable
private fun PreviewAccountListItem_Remit() {
    AccountListItem_Remit(
        modifier = Modifier,
        accountNumber = "accountNumber",
        balance = 10000,
        accountName = "accountName",
        companyLogoPath = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png",
        onClickItem = {},
        onClickRemit = {}
    )
}
