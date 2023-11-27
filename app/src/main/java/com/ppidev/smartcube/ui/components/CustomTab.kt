package com.ppidev.smartcube.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomTab(
    currentPage: Int,
    onChangePage: (page: Int) -> Unit,
    listPage: List<String>
) {
    TabRow(
        selectedTabIndex = currentPage,
        modifier = Modifier
            .height(24.dp)
            .clip(RoundedCornerShape(4.dp)),
        divider = {
            Spacer(modifier = Modifier.size(8.dp))
        },
        indicator = { tabPositions ->
            Box {
            }
        },
    ) {
        listPage.forEachIndexed { idx, name ->
            Tab(
                selected = currentPage == idx,
                onClick = {
                    onChangePage(idx)
                },
                text = {
                    Text(
                        text = name,
                        color = if (currentPage == idx) Color.White else Color.Black
                    )
                },
                modifier = if (currentPage == idx) {
                    Modifier
                        .height(24.dp)
                        .padding(horizontal = 24.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            MaterialTheme.colorScheme.primary
                        )
                } else {
                    Modifier
                        .height(24.dp)
                        .padding(horizontal = 24.dp)
                        .background(
                            Color.White
                        )
                }
            )
        }

    }
}
