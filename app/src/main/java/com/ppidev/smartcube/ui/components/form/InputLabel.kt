package com.ppidev.smartcube.ui.components.form

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import com.ppidev.smartcube.ui.theme.Typography


@Composable
fun InputLabel(
    label: String,
    isRequired: Boolean = false
) {
    Text(
        text = buildAnnotatedString {
            val labelSpan =
                Typography.bodyMedium.toSpanStyle()
            val labelRequired =
                Typography.bodyMedium.copy(
                    color = Color.Red,
                    fontWeight = FontWeight.Medium
                ).toSpanStyle()
            append(AnnotatedString(label, spanStyle = labelSpan))
            if (isRequired) append(AnnotatedString(text = " *", spanStyle = labelRequired))
        },
    )
}