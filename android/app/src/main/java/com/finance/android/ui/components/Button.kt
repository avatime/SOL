package com.finance.android.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.R
import com.finance.android.ui.theme.MainColor
import com.finance.android.ui.theme.White
import com.finance.android.utils.ext.withBottomButton

enum class ButtonColor {
    PRIMARY,
    WHITE;

    fun getBackgroundColor(): Color {
        return when (this) {
            PRIMARY -> MainColor
            WHITE -> White
        }
    }

    fun getTextColor(): Color {
        return when (this) {
            PRIMARY -> White
            WHITE -> MainColor
        }
    }
}

enum class ButtonType {
    ROUNDED,
    CIRCULAR;

    fun getShape(): Shape? {
        return when (this) {
            ROUNDED -> RoundedCornerShape(20)
            CIRCULAR -> null
        }
    }
}

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    buttonColor: ButtonColor = ButtonColor.PRIMARY,
    buttonType: ButtonType,
    enabled: Boolean = true,
    fontSize: TextUnit = dimensionResource(id = R.dimen.font_size_btn_bottom_text).value.sp
) {
    if (buttonColor == ButtonColor.PRIMARY) {
        Button(
            onClick = onClick,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor.getBackgroundColor(),
                contentColor = buttonColor.getTextColor()
            ),
            contentPadding = PaddingValues(0.dp),
            shape = buttonType.getShape() ?: ButtonDefaults.shape,
            enabled = enabled
        ) {
            Text(
                text = text,
                fontSize = fontSize
            )
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = buttonColor.getBackgroundColor(),
                contentColor = buttonColor.getTextColor()
            ),
            contentPadding = PaddingValues(0.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            shape = buttonType.getShape() ?: ButtonDefaults.shape,
            enabled = enabled
        ) {
            Text(
                text = text,
                fontSize = fontSize
            )
        }
    }
}

@Preview
@Composable
fun RoundedTextButton() {
    TextButton(
        onClick = { /*TODO*/ },
        text = "프리뷰",
        modifier = Modifier,
        buttonType = ButtonType.ROUNDED
    )
}

@Preview
@Composable
fun CircularTextButton() {
    TextButton(
        onClick = { /*TODO*/ },
        text = "프리뷰",
        modifier = Modifier,
        buttonType = ButtonType.CIRCULAR
    )
}

@Preview
@Composable
fun RoundedWhiteTextButton() {
    TextButton(
        onClick = { /*TODO*/ },
        text = "프리뷰",
        modifier = Modifier.withBottomButton(),
        buttonType = ButtonType.ROUNDED,
        buttonColor = ButtonColor.WHITE
    )
}

@Preview
@Composable
fun CircularWhiteTextButton() {
    TextButton(
        onClick = { /*TODO*/ },
        text = "프리뷰",
        modifier = Modifier,
        buttonType = ButtonType.CIRCULAR,
        buttonColor = ButtonColor.WHITE
    )
}
