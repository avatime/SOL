package com.finance.android.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
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
    onClick: () -> Unit,
    text: String,
    buttonColor: ButtonColor = ButtonColor.PRIMARY,
    modifier: Modifier,
    buttonType: ButtonType
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor.getBackgroundColor(),
            contentColor = buttonColor.getTextColor()
        ),
        shape = buttonType.getShape() ?: ButtonDefaults.shape,
    ) {
        Text(text = text)
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

