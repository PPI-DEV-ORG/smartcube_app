package com.ppidev.smartcube.domain.use_case.edge_server

import com.ppidev.smartcube.common.EExceptionCode
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.contract.data.repository.IEdgeServerRepository
import com.ppidev.smartcube.contract.domain.use_case.edge_server.IAddEdgeServerUseCase
import com.ppidev.smartcube.data.remote.dto.CreateEdgeServerDto
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddEdgeServerUseCase @Inject constructor(
    private val edgeServerRepository: Lazy<IEdgeServerRepository>
) : IAddEdgeServerUseCase {
    override fun invoke(
        name: String,
        vendor: String,
        description: String
    ): Flow<Resource<ResponseApp<CreateEdgeServerDto?>>> = flow {
        emit(Resource.Loading())
        emit(addEdgeServer(name, vendor, description))
    }

    private suspend fun addEdgeServer(
        name: String,
        vendor: String,
        description: String
    ) : Resource<ResponseApp<CreateEdgeServerDto?>> {
        try {
            val response = edgeServerRepository.get().addEdgeServer(name, vendor, description)

            if (!response.status) {
                return Resource.Error(
                    response.statusCode,
                    response.message
                )
            }

            return Resource.Success(response)
        } catch (e: Exception) {
            return Resource.Error(
                EExceptionCode.UseCaseError.code,
                e.message ?: "Something wrong"
            )
        }
    }

}