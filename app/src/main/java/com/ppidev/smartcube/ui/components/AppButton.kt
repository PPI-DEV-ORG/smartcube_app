package com.ppidev.smartcube.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


enum class EButtonType(val type: String) {
    PRIMARY("primary"), SECONDARY("secondary"), OUTLINE("outline")
}

enum class EButtonIconPosition(val position: Boolean) {
    LEFT(false), Right(true)
}

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    type: EButtonType = EButtonType.PRIMARY,
    icon: (@Composable () -> Unit)? = null,
    iconPosition: EButtonIconPosition? = null,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(6.dp),
        border = when (type) {
            EButtonType.OUTLINE -> BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            else -> null
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = when (type) {
                EButtonType.PRIMARY -> MaterialTheme.colorScheme.primary
                EButtonType.SECONDARY -> MaterialTheme.colorScheme.secondary
                else -> Color.Transparent
            }, contentColor = when (type) {
                EButtonType.OUTLINE -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.onPrimary
            }
        )
    ) {
        if (iconPosition == EButtonIconPosition.LEFT && icon != null) {
            icon()
            Spacer(modifier = Modifier.size(10.dp))
        }
        Text(text = "Button")
        if (iconPosition == EButtonIconPosition.Right && icon != null) {
            Spacer(modifier = Modifier.size(10.dp))
            icon()
        }
    }
}

@Preview(apiLevel = 34)
@Composable
fun PreviewAppButton() {
    Column {
        AppButton(
            onClick = {},
            icon = {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            },
            iconPosition = EButtonIconPosition.LEFT,
            type = EButtonType.PRIMARY
        )

        AppButton(
            onClick = {},
            icon = {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            },
            iconPosition = EButtonIconPosition.LEFT,
            type = EButtonType.SECONDARY
        )

        AppButton(
            onClick = {},
            icon = {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            },
            iconPosition = EButtonIconPosition.LEFT,
            type = EButtonType.OUTLINE
        )
    }
}