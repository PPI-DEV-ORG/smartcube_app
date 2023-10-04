package com.ppidev.smartcube.domain.permission

import android.Manifest
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationPermissions(
    permissionState: PermissionState
) {
    LaunchedEffect(key1 = Unit) {
        permissionState.launchPermissionRequest()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPermission() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val permissionNotificationState =
            rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
        NotificationPermissions(permissionState = permissionNotificationState)
    }
}