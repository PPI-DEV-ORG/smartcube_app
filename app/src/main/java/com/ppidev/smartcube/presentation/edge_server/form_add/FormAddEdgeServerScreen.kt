package com.ppidev.smartcube.presentation.edge_server.form_add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ppidev.smartcube.ui.components.TagLabel
import com.ppidev.smartcube.ui.components.form.CustomInputField
import com.ppidev.smartcube.ui.theme.LightGreen
import com.ppidev.smartcube.utils.bottomBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormAddEdgeServerScreen() {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        TopAppBar(
            modifier = Modifier.bottomBorder(1.dp, Color.LightGray),
            title = {
            Text(
                text = "Add Edge Server", style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            )
        })
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(48.dp)
//                .background(color = Color.White)
//                .bottomBorder(1.dp, Color.LightGray)
//                .padding(horizontal = 16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
//            ClickableText(
//                text = AnnotatedString("Cancel"), onClick = {}, style = TextStyle(
//                    color = Color.Red
//                )
//            )
//
//            Text(
//                text = "Add Edge Server", style = TextStyle(
//                    fontWeight = FontWeight.Medium,
//                    fontSize = 16.sp
//                )
//            )
//
//            ClickableText(
//                text = AnnotatedString("Save"), onClick = {}, style = TextStyle(
//                    color = LightGreen
//                )
//            )
//        }

        Spacer(modifier = Modifier.size(44.dp))

        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 20.dp)
        ) {
            TagLabel(modifier = Modifier.offset(y = (-24).dp),
                text = "Warning",
                backgroundColor = ButtonDefaults.buttonColors(Color.LightGray),
                textStyle = TextStyle(
                    color = Color.Red
                ),
                iconStart = true,
                icon = {
                    Icon(imageVector = Icons.Outlined.Warning, contentDescription = "warning")
                })

            Text(
                modifier = Modifier.padding(top = 32.dp, bottom = 19.dp),
                text = "Before adding devices, you should configure the multiprocessing script on your edge server.\n" +
                        "Please contact smartcube team to help you on configure edge server.",
                style = TextStyle(
                    textAlign = TextAlign.Justify,
                )
            )
        }

        Spacer(modifier = Modifier.size(32.dp))
        CardItemAddServer(
            titleTag = "Server",
            serverName = "",
            description = "",
            serverVendor = "",
            onServerNameChange = { /*TODO*/ },
            onDescriptionChange = { /*TODO*/ }) {

        }
        Spacer(modifier = Modifier.size(32.dp))

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FormAddEdgeServerScreenPreview() {
    FormAddEdgeServerScreen()
}

@Composable
fun CardItemAddServer(
    titleTag: String,
    serverName: String,
    description: String,
    serverVendor: String,
    onServerNameChange: () -> Unit,
    onDescriptionChange: () -> Unit,
    onServerVendorChange: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 20.dp)
    ) {
        TagLabel(modifier = Modifier.offset(y = (-24).dp), text = titleTag)

        Column(
            modifier = Modifier.padding(vertical = 32.dp)
        ) {
            CustomInputField(
                label = "Edge Server Name",
                text = serverName,
                errorText = "",
                onTextChanged = { onServerNameChange() })
            CustomInputField(
                label = "Description",
                text = description,
                errorText = "",
                onTextChanged = { onDescriptionChange() })
            CustomInputField(
                label = "Edge Vendor Name",
                text = serverVendor,
                errorText = "",
                onTextChanged = { onServerVendorChange() })
        }
    }
}