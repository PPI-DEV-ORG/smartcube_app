package com.ppidev.smartcube.presentation.dashboard

data class DashboardState(
    val isLoading: Boolean = false,
    val serverSummary: ServerSummary = ServerSummary("", "", "", ""),
) {
    data class ServerSummary(
        val cpuAvgTemp: String,
        val ramFreeSpace: String,
        val storageFreeSpace: String,
        val fanSpeed: String
    )
}

