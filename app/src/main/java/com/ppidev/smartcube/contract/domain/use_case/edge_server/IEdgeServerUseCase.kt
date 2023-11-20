package com.ppidev.smartcube.contract.domain.use_case.edge_server

import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.data.remote.dto.CreateEdgeServerDto
import kotlinx.coroutines.flow.Flow

interface IAddEdgeServerUseCase {
    operator fun invoke(
        name: String,
        vendor: String,
        description: String
    ): Flow<Resource<ResponseApp<CreateEdgeServerDto?>>>
}