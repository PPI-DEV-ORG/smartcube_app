package com.ppidev.smartcube.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun AppScrollableTabLayout(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    listItems: List<String>,
    onTabChange: (index: Int, item: String) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            edgePadding = 12.dp,
            containerColor = Color.Transparent,
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .zIndex(-1f)
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .padding(2.dp)
                        .fillMaxHeight()
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(4.dp)
                        )
                )
            },
            divider = {

            }
        ) {
            listItems.forEachIndexed { tabIndex, item ->
                Tab(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    selected = selectedTabIndex == tabIndex,
                    onClick = { onTabChange(tabIndex, item) },
                    text = {
                        Text(
                            text = item,
                            color = if (tabIndex != selectedTabIndex) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onPrimary
                        )
                    }
                )

            }
        }

        Spacer(modifier = Modifier.size(10.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun AppScrollableTabListPreview() {
    AppScrollableTabLayout(
        selectedTabIndex = 0,
        listItems = listOf("tab1", "tab2"),
        onTabChange = { _, _ ->

        },
    )
}