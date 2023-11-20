package com.ppidev.smartcube.contract.data.repository

import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.data.remote.dto.CreateEdgeServerDto

interface IEdgeServerRepository {
    suspend fun addEdgeServer(
        name: String,
        vendor: String,
        description: String
    ): ResponseApp<CreateEdgeServerDto?>
}