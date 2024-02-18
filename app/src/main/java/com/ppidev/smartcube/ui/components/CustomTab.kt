package com.ppidev.smartcube.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomTab(
    modifier: Modifier = Modifier,
    currentPage: Int,
    onChangePage: (page: Int) -> Unit,
    listPage: List<String>
) {
    TabRow(
        selectedTabIndex = currentPage,
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(RoundedCornerShape(4.dp)),
        divider = {
        },
        indicator = { _ ->
            Box {
            }
        }
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
                        .padding(
                            start = if ((idx+1) % 2 == 0) 12.dp else 0.dp,
                            end = if ((idx+1) % 2 != 0) 12.dp else 0.dp
                        )
                        .height(40.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            MaterialTheme.colorScheme.primary
                        )
                } else {
                    Modifier
                        .height(40.dp)
                        .background(
                            Color.White
                        )
                }
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun CustomTabPreview() {
    CustomTab(currentPage = 0, onChangePage = {}, listPage = listOf("TAB 1", "TAB 2"))
}