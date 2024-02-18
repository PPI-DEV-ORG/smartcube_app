package com.ppidev.smartcube.domain.use_case.edge_server

import com.ppidev.smartcube.contract.data.repository.IEdgeServerRepository
import com.ppidev.smartcube.contract.domain.use_case.edge_server.IListEdgeServerUseCase
import com.ppidev.smartcube.data.remote.dto.EdgeServerItemDto
import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListEdgeServerUseCase @Inject constructor(
    private val edgeServerRepository: Lazy<IEdgeServerRepository>
) : IListEdgeServerUseCase {
    override fun invoke(): Flow<Resource<List<EdgeServerItemDto>, Any>> = flow {
        emit(Resource.Loading())
        emit(getListEdgeServer())
    }

    private suspend fun getListEdgeServer(): Resource<List<EdgeServerItemDto>, Any> {
        try {
            val response = edgeServerRepository.get().listEdgeServer()
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