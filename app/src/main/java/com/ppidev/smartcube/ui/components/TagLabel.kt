package com.ppidev.smartcube.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TagLabel(
    text: String,
    modifier: Modifier = Modifier,
    iconStart: Boolean = false,
    icon: (@Composable () -> Unit)? = null,
    textStyle: TextStyle = TextStyle(),
    backgroundColor: ButtonColors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
) {
    Button(
        modifier = modifier,
        onClick = {},
        shape = RoundedCornerShape(32),
        colors = backgroundColor
    ) {
        if (iconStart && icon != null) {
            icon.invoke()
            Spacer(modifier = Modifier.size(8.dp))
        }
        Text(text = text, style = textStyle)
        if (!iconStart && icon != null) {
            Spacer(modifier = Modifier.size(8.dp))
            icon.invoke()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TagLabelPreview() {
    TagLabel(text = "label")
}