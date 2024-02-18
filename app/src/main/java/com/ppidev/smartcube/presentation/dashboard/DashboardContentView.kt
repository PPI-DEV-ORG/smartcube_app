package com.ppidev.smartcube.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
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
import androidx.compose.ui.text.style.TextOverflow
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
    ramFree: String = "",
    fanSpeed: String = "",
    upTime: String = "",
    errors: DashboardState.Error,
    ramUsage: Float = 0f,
    isLoadingListServer: Boolean,
    isLoadingListDevices: Boolean,
    isLoadingProfile: Boolean,
    navigateCreateNewServer: () -> Unit,
    navigateCreateNewDevice: () -> Unit,
    navigateToDetailDevice: (index: Int, device: EdgeDevice) -> Unit
) {

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            bottom = 160.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item(
            span = { GridItemSpan(2) }
        ) {
            Column {
                Spacer(modifier = Modifier.size(12.dp))
                UserSection(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    username = username,
                    email = email,
                    isLoading = isLoadingProfile
                )
            }
        }

        if (isLoadingListServer) {
            item(
                span = { GridItemSpan(2) }
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(162.dp)
                            .clip(
                                RoundedCornerShape(8.dp)
                            )
                            .shimmerEffect()
                    ) {}

                    Spacer(modifier = Modifier.size(12.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(92.dp)
                                .height(32.dp)
                                .clip(
                                    RoundedCornerShape(4.dp)
                                )
                                .shimmerEffect()

                        ) {}

                        Box(
                            modifier = Modifier
                                .width(92.dp)
                                .height(32.dp)
                                .clip(
                                    RoundedCornerShape(4.dp)
                                )
                                .shimmerEffect()
                        ) {}

                        Box(
                            modifier = Modifier
                                .width(92.dp)
                                .height(32.dp)
                                .clip(
                                    RoundedCornerShape(4.dp)
                                )
                                .shimmerEffect()
                        ) {}
                    }
                }
            }
        } else if (errors.listServerError.isNotEmpty()) {
            item(
                span = { GridItemSpan(2) }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .padding(top = 118.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.thumb_error_504),
                        contentDescription = errors.listServerError
                    )
                }
            }
        } else {
            if (listEdgeServer.isEmpty()) {
                item(
                    span = { GridItemSpan(2) }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
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
                            modifier = Modifier.width(200.dp),
                            text = "Create Your Cluster", style = TextStyle(
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
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            ),
                            onClick = {
                                navigateCreateNewServer()
                            }) {
                            Text(text = "Add Cluster", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            } else {
                item(
                    span = { GridItemSpan(2) }
                ) {
                    Column {
                        CardServerInfo(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            avgCpuTemp = avgCpuTemp,
                            totalRam = totalRam,
                            fanSpeed = fanSpeed,
                            upTime = upTime,
                            ramUsage = ramUsage,
                            ramFree = ramFree
                        )
                    }
                }

                if (listEdgeServer.count() > 1) {
                    item(
                        span = { GridItemSpan(2) }
                    ) {
                        AppScrollableTabLayout(
                            selectedTabIndex = selectedTabIndex,
                            listItems = listEdgeServer,
                            onTabChange = { index, name -> onTabChange(index, name) })
                    }
                }

                listGridDevice(
                    isLoadingListDevices = isLoadingListDevices,
                    listEdgeDevices = listEdgeDevices,
                    navigateToDetailDevice = navigateToDetailDevice,
                    navigateCreateNewDevice = navigateCreateNewDevice
                )
            }
        }
    }
}

fun LazyGridScope.listGridDevice(
    isLoadingListDevices: Boolean,
    listEdgeDevices: List<EdgeDevice>,
    navigateToDetailDevice: (index: Int, item: EdgeDevice) -> Unit,
    navigateCreateNewDevice: () -> Unit
) {
    if (isLoadingListDevices) {
        items(count = 2) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(176.dp)
                    .padding(
                        start = if (it % 2 == 0) 16.dp else 0.dp,
                        end = if (it % 2 != 0) 16.dp else 0.dp
                    )
                    .clip(
                        RoundedCornerShape(4.dp)
                    )
                    .shimmerEffect()
            )
        }
    } else {
        itemsIndexed(
            items = listEdgeDevices,
            key = { _, item -> item.id }) { index, item ->

            CardItemDevice(
                modifier = if (index % 2 == 0) Modifier.padding(start = 16.dp) else Modifier.padding(
                    end = 16.dp
                ),
                deviceType = item.type,
                vendorName = item.vendorName,
                status = true,
                onClick = {
                    navigateToDetailDevice(index, item)
                }
            )
        }

        item {
            CardAddDevice(
                modifier = if (listEdgeDevices.count() % 2 == 0) Modifier.padding(start = 16.dp) else Modifier.padding(
                    end = 16.dp
                ),
                onClick = {
                    navigateCreateNewDevice()
                }
            )
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
        errors = DashboardState.Error(),
        navigateCreateNewServer = {},
        navigateToDetailDevice = { _, _ -> },
        navigateCreateNewDevice = {}
    )
}

@Composable
fun UserSection(
    modifier: Modifier = Modifier,
    username: String = "Jhon doew",
    email: String = "Jhon@gmail.com",
    isLoading: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(24.dp)
                        .clip(
                            RoundedCornerShape(4.dp)
                        )
                        .shimmerEffect()
                ) {}

                Spacer(modifier = Modifier.size(3.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(16.dp)
                        .clip(
                            RoundedCornerShape(3.dp)
                        )
                        .shimmerEffect()
                ) {}
            } else {
                Text(
                    text = "Hi, $username",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.size(2.dp))
                Text(
                    text = email,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Image(
            painter = painterResource(R.drawable.app_logo),
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UserSectionPreview() {
    UserSection(isLoading = true)
}