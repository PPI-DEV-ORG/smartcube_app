package com.ppidev.smartcube.domain.use_case.edge_server

import com.ppidev.smartcube.contract.data.repository.IEdgeServerRepository
import com.ppidev.smartcube.contract.domain.use_case.edge_server.IAddEdgeServerUseCase
import com.ppidev.smartcube.data.remote.dto.CreateEdgeServerDto
import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
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
    ): Flow<Resource<CreateEdgeServerDto?, Any>> = flow {
        emit(Resource.Loading())
        emit(addEdgeServer(name, vendor, description))
    }

    private suspend fun addEdgeServer(
        name: String,
        vendor: String,
        description: String
    ): Resource<CreateEdgeServerDto?, Any> {
        try {
            val response = edgeServerRepository.get().addEdgeServer(name, vendor, description)

            if (!response.status) {
                return Resource.Error(
                    response.statusCode,
                    response.message
                )
            }

            return Resource.Success(response.message, response.data)
        } catch (e: Exception) {
            return Resource.Error(
                EExceptionCode.UseCaseError.code,
                e.message ?: "Something wrong"
            )
        }
    }

}