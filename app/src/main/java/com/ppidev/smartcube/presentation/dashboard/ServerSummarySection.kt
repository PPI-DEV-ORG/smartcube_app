package com.ppidev.smartcube.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ppidev.smartcube.R
import com.ppidev.smartcube.domain.model.DeviceStatusModel
import com.ppidev.smartcube.presentation.dashboard.model.ResourceItem
import com.ppidev.smartcube.ui.components.HeaderSection

@Composable
fun ServerSummarySection(
    modifier: Modifier = Modifier,
    resourceState: DeviceStatusModel
) {
    val listResource: List<ResourceItem> = listOf(
        ResourceItem(
            title = "RAM",
            status = resourceState.memoryFree,
            icon = R.drawable.ic_memory
        ),
        ResourceItem(
            title = "Storage",
            status = resourceState.storageFree,
            icon = R.drawable.ic_memory
        ),
        ResourceItem(title = "Up time", status = resourceState.upTime, icon = R.drawable.ic_memory),
        ResourceItem(
            title = "Fan Speed",
            status = resourceState.fanSpeed,
            icon = R.drawable.ic_memory
        )
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(230.dp)
    ) {
        Column(
            modifier = Modifier
                .width(146.dp)
                .padding(horizontal = 2.dp)
        ) {
            Card(
                Modifier.fillMaxSize(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            ) {
                Column(Modifier.padding(top = 50.dp, start = 10.dp, end = 10.dp)) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        text = "${resourceState.cpuTemp}°", style = TextStyle(
                            fontSize = 55.sp, fontWeight = FontWeight(700),
                            textAlign = TextAlign.End
                        )
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 29.dp),
                        text = "CPU Temp (°C)",
                        style = TextStyle(
                            fontSize = 14.sp,
                            textAlign = TextAlign.End
                        )
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 2.dp)
        ) {
            Card(Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp, end = 10.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        listResource.map {
                            CardResource(it)
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun CardResource(resource: ResourceItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.padding(end = 10.dp)) {
            Card(
                modifier = Modifier.size(44.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = resource.icon),
                            contentDescription = resource.title
                        )
                    }

                }
            }
        }

        Column {
            Text(text = resource.title)
            Text(text = resource.status, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ServerSummaryPreview() {
    ServerSummarySection(
        resourceState = DeviceStatusModel(
            "70",
            "30",
            "40",
            "40",
            "90"
        )
    )
}