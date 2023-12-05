package com.ppidev.smartcube.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp


@Composable
fun HeaderSection(
    modifier: Modifier = Modifier,
    title: String,
    isShowMoreExist: Boolean = false,
    textShowMore: String = "Show More",
    onClickShowMore: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title)

        if (isShowMoreExist && onClickShowMore != null) {
            ClickableText(text = AnnotatedString(textShowMore), onClick = {onClickShowMore()})
        }
    }
}