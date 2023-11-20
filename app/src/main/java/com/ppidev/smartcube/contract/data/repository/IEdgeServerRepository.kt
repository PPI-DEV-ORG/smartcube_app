package com.ppidev.smartcube.contract.data.repository

import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.data.remote.dto.CreateEdgeServerDto
import com.ppidev.smartcube.data.remote.dto.EdgeServerItemDto

interface IEdgeServerRepository {
    suspend fun addEdgeServer(
        name: String,
        vendor: String,
        description: String
    ): ResponseApp<CreateEdgeServerDto?>

    suspend fun listEdgeServer(): ResponseApp<List<EdgeServerItemDto>>
}