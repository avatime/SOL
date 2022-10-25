package com.finance.android.utils.ext

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.finance.android.R

fun Modifier.withBottomButton() = composed {
    this
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.padding_medium))
        .height(60.dp)
}