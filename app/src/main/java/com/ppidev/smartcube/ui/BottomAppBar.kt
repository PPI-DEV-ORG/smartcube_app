package com.ppidev.smartcube.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomAppBar(
    navController: NavHostController
) {
    val items = listOf(
        NavigationItem(
            title = "Notifications",
            unselectedIcon = Icons.Outlined.Notifications,
            selectedIcon = Icons.Filled.Notifications,
            hasNews = false,
            badgeCount = null,
            screen = Screen.Notifications
        ),
        NavigationItem(
            title = "Dashboard",
            unselectedIcon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home,
            hasNews = false,
            badgeCount = null,
            screen = Screen.Dashboard
        ),
        NavigationItem(
            title = "Dashboard",
            unselectedIcon = Icons.Outlined.Person,
            selectedIcon = Icons.Filled.Person,
            hasNews = false,
            badgeCount = null,
            screen = Screen.Profile
        )
    )

    var selectedItemIndex: Int by rememberSaveable {
        mutableIntStateOf(1)
    }

    NavigationBar(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .clip(
                RoundedCornerShape(48.dp)
            )
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                label = {
                    Text(text = item.title)
                },
                alwaysShowLabel = false,
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badgeCount != null) {
                                Badge {
                                    Text(text = item.badgeCount.toString())
                                }
                            } else if (item.hasNews) {
                                Badge()
                            }
                        }
                    ) {
                        (if (index == selectedItemIndex) {
                            item.selectedIcon
                        } else item.unselectedIcon).let {
                            Icon(
                                imageVector = it,
                                contentDescription = item.title
                            )
                        }
                    }
                },
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.screen.screenRoute) {
                        navController.graph.startDestinationRoute?.let { screenRoute ->
                            popUpTo(screenRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }

}

@Preview
@Composable
fun BottomAppBarPreview() {
    BottomAppBar(
        navController = rememberNavController()
    )
}
