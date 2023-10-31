package com.ppidev.smartcube.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ppidev.smartcube.R
import com.ppidev.smartcube.domain.model.MLModel
import com.ppidev.smartcube.presentation.dashboard.model.dummyListModelObjectDetection
import com.ppidev.smartcube.ui.components.HeaderSection

//@Composable
//fun ListModelSection(
//    modifier: Modifier = Modifier,
//    models: List<MLModel> = emptyList()
//) {
//    Column(
//        modifier = modifier
//            .fillMaxWidth()
//    ) {
//        HeaderSection(title = "List models", isShowMoreExist = true, onClickShowMore = {})
//
//        Column(
//            modifier = Modifier
//                .fillMaxWidth(),
//            verticalArrangement = Arrangement.spacedBy(14.dp)
//        ) {
//            models.map {
//                CardModelItem(modelName = it.name, version = it.version)
//            }
//        }
//    }
//}


@Composable
fun CardModelItem(modelName: String, version: String) {
    Card(
        Modifier.height(60.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = modelName)
                Text(text = version)
            }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_open_eye),
                    contentDescription = modelName
                )
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun CardModelItemPreview() {
    CardModelItem(modelName = "Fire and smoke detection", version = "1.0")
}