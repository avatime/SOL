package com.finance.android.utils.ext

import android.content.Context
import androidx.compose.ui.unit.Dp
import kotlin.math.roundToInt

fun Dp.toPx(context: Context): Int {
    val density: Float = context.resources.displayMetrics.density
    return (value * density).roundToInt()
}
