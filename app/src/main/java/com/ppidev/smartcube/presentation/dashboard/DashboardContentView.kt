package com.ppidev.smartcube.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ppidev.smartcube.R
import com.ppidev.smartcube.data.remote.dto.EdgeDevice
import com.ppidev.smartcube.ui.components.AppScrollableTabLayout
import com.ppidev.smartcube.ui.components.card.CardAddDevice
import com.ppidev.smartcube.ui.components.card.CardItemDevice
import com.ppidev.smartcube.ui.components.card.CardServerInfo
import com.ppidev.smartcube.ui.components.shimmerEffect

@Composable
fun DashboardContentView(
    modifier: Modifier = Modifier,
    username: String,
    email: String,
    selectedTabIndex: Int,
    listEdgeServer: List<String>,
    listEdgeDevices: List<EdgeDevice>,
    onTabChange: (index: Int, name: String) -> Unit,
    avgCpuTemp: String = "",
    totalRam: String = "",
    fanSpeed: String = "",
    upTime: String = "",
    ramUsage: Int = 0,
    isLoadingListServer: Boolean,
    isLoadingListDevices: Boolean,
    isLoadingProfile: Boolean,
    navigateCreateNewServer: () -> Unit,
    navigateToDetailDevice: (deviceId: UInt) -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        UserSection(username = username, email = email, isLoading = isLoadingProfile)

        if (isLoadingListServer) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(162.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .shimmerEffect()
            ) {}

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(90.dp)
                        .height(32.dp)
                        .clip(
                            RoundedCornerShape(4.dp)
                        )
                        .shimmerEffect()
                ) {}

                Box(
                    modifier = Modifier
                        .width(90.dp)
                        .height(32.dp)
                        .clip(
                            RoundedCornerShape(4.dp)
                        )
                        .shimmerEffect()
                ) {}

                Box(
                    modifier = Modifier
                        .width(90.dp)
                        .height(32.dp)
                        .clip(
                            RoundedCornerShape(4.dp)
                        )
                        .shimmerEffect()
                ) {}
            }

        } else {
            if (listEdgeServer.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 118.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(28.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .width(254.dp)
                            .height(254.dp),
                        painter = painterResource(id = R.drawable.empty_dashboard),
                        contentDescription = null
                    )
                    Text(
                        modifier= Modifier.width(200.dp),
                        text = "Start To Create Your First Server", style = TextStyle(
                            fontSize = 24.sp,
                            lineHeight = 32.sp,
                            fontWeight = FontWeight(600),
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Center,
                        )
                    )
                    Button(modifier = Modifier
                        .shadow(
                            elevation = 2.dp,
                            spotColor = Color(0xFF6750A4),
                            ambientColor = Color(0xFF6750A4)
                        )
                        .width(230.dp)
                        .height(44.dp),
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0x80F3F3F3), contentColor = Color.Gray),
                        onClick = {
                            navigateCreateNewServer()
                        }) {
                        Text(text = "Add Server", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            } else {
                CardServerInfo(
                    avgCpuTemp = avgCpuTemp,
                    totalRam = totalRam,
                    fanSpeed = fanSpeed,
                    upTime = upTime,
                    ramUsage = ramUsage
                )

                if (listEdgeServer.count() == 1) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(
                            bottom = 16.dp
                        ),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        itemsIndexed(
                            items = listEdgeDevices,
                            key = { _, item -> item.id }) { _, item ->
                            CardItemDevice(
                                deviceType = item.type,
                                vendorName = item.vendorName,
                                status = true
                            )
                        }
                    }
                }

                if (listEdgeServer.count() > 1) {
                    AppScrollableTabLayout(
                        selectedTabIndex = selectedTabIndex,
                        listItems = listEdgeServer,
                        onTabChange = { index, name -> onTabChange(index, name) })
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(
                        bottom = 16.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (isLoadingListDevices) {
                        items(count = 2) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(176.dp)
                                    .clip(
                                        RoundedCornerShape(4.dp)
                                    )
                                    .shimmerEffect()
                            )
                        }
                    } else {
                        itemsIndexed(
                            items = listEdgeDevices,
                            key = { _, item -> item.id }) { _, item ->
                            CardItemDevice(
                                deviceType = item.type,
                                vendorName = item.vendorName,
                                status = true
                            )
                        }

                        item {
                            CardAddDevice(
                                onClick = {}
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardContentViewPreview() {
    DashboardContentView(
        username = "Jhon",
        email = "jhon@gmail.com",
        selectedTabIndex = 0,
        listEdgeServer = listOf("Server A", "Server B"),
        listEdgeDevices = emptyList(),
        onTabChange = { _, _ -> },
        isLoadingListServer = false,
        isLoadingListDevices = false,
        isLoadingProfile = false,
        navigateCreateNewServer = {},
        navigateToDetailDevice =  {}
    )
}