package com.ppidev.smartcube.contract.data.repository

import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.data.remote.dto.EdgeDevicesInfoDto

interface IEdgeDeviceRepository {
    suspend fun getEdgeDevicesInfo(edgeServerId: UInt): ResponseApp<EdgeDevicesInfoDto?>
}