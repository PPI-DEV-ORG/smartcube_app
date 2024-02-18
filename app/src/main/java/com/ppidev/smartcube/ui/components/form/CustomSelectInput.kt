package com.ppidev.smartcube.ui.components.form

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CustomSelectInput(
    modifier: Modifier = Modifier,
    label: String = "Label",
    expanded: Boolean,
    value: String,
    onExpandedChange: () -> Unit,
    onDismissRequest: () -> Unit,
    listMenu: @Composable () -> Unit,
    errorText: String? = null,
    customLabel: (@Composable () -> Unit)? = null
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        if (customLabel != null) {
            customLabel()
        } else {
            Text(text = label)
        }
        Spacer(modifier = Modifier.size(4.dp))
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = { onExpandedChange() })
        {
            Column {
                TextField(
                    value = value,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .border(
                            BorderStroke(width = 2.dp, color = Color.LightGray),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                )

                if (errorText != null) {
                    Text(
                        text = errorText, style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.Red
                        )
                    )
                }
            }

            DropdownMenu(
                modifier = Modifier.exposedDropdownSize(matchTextFieldWidth = true),
                expanded = expanded,
                onDismissRequest = { onDismissRequest() }
            ) {
                listMenu()
            }
        }
    }
}

