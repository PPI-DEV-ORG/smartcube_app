package com.ppidev.smartcube.ui.components.form

import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ppidev.smartcube.ui.theme.Neutral70
import com.ppidev.smartcube.ui.theme.SmartcubeAppTheme


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppInput(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.onBackground
    ),
    text: String,
    errorText: String,
    errorTestTag: String = "",
    placeholder: String = "Placeholder",
    label: String = "Label",
    iconStart: Boolean = true,
    showText: Boolean = true,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    onTextChanged: (String) -> Unit,
    customLabel: (@Composable () -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    onClickIcon: (() -> Unit)? = null
) {
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState().value

    Column {
        if (customLabel != null) {
            customLabel()
        } else {
            Text(text = label)
        }
        Spacer(modifier = Modifier.size(5.dp))
        BasicTextField(
            modifier = modifier,
            singleLine = true,
            enabled = enabled,
            value = text,
            textStyle = textStyle,
            interactionSource = interactionSource,
            visualTransformation = if (showText) VisualTransformation.None else PasswordVisualTransformation(),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = when (isFocused) {
                                true -> MaterialTheme.colorScheme.primary
                                else -> Color(0xFFE6E6E6)
                            }, shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 20.dp, vertical = 19.dp)
                        .focusRequester(focusRequester)
                        .focusable(interactionSource = interactionSource),
                    horizontalArrangement = if (iconStart) Arrangement.Start else Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (icon != null && iconStart) {
                        IconButton(modifier = Modifier.size(24.dp), onClick = onClickIcon ?: {}) {
                            icon.invoke()
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                    }

                    Box(
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        innerTextField()

                        if (text.isEmpty()) {
                            Text(
                                maxLines = 1,
                                text = placeholder,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF999999),
                            )
                        }

                    }

                    if (icon != null && !iconStart) {
                        Spacer(modifier = Modifier.size(10.dp))
                        IconButton(
                            modifier = Modifier.size(24.dp),
                            onClick = onClickIcon ?: {},
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = Neutral70,
                            )
                        ) {
                            icon.invoke()
                        }
                    }
                }
            },
            onValueChange = {
                onTextChanged(it)
            },
        )

        Spacer(modifier = Modifier.size(5.dp))

        if (errorText.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .semantics { testTagsAsResourceId = true }
                    .testTag(errorTestTag),
                text = errorText, style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Red
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DynamicTextFieldPreview() {
    SmartcubeAppTheme {
        val textState = remember { mutableStateOf("") }
        AppInput(
            text = textState.value,
            onTextChanged = { newText ->
                textState.value = newText
            },
            placeholder = "Enter text here",
            iconStart = false,
            icon = {
                Icon(imageVector = Icons.Filled.RemoveRedEye, contentDescription = "icon")
            },
            errorText = ""
        )
    }
}