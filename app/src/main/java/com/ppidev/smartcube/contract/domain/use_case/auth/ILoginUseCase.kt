package com.ppidev.smartcube.contract.domain.use_case.auth

import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.common.Response
import com.ppidev.smartcube.data.remote.dto.LoginDto
import kotlinx.coroutines.flow.Flow

interface ILoginUseCase {
    operator fun invoke(email: String, password: String): Flow<Resource<Response<LoginDto?>>>
}