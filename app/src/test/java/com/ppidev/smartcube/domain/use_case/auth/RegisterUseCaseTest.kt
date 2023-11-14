package com.ppidev.smartcube.domain.use_case.auth

import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.contract.data.repository.IAuthRepository
import com.ppidev.smartcube.data.remote.dto.RegisterDto
import dagger.Lazy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class RegisterUseCaseTest {
    private lateinit var registerUseCase: RegisterUseCase

    @Mock
    private var authRepository = mock<IAuthRepository>()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        val lazy = Lazy { authRepository }
        registerUseCase = RegisterUseCase(lazy)
    }

    @Test
    fun `test register failed when email is not valid`() = runBlocking {
        val username = "testingUser"
        val wrongEmail = "testing@"
        val password = "freya123"
        val confirmPassword = "freya123"

        `when`(authRepository.register(username, wrongEmail, password, confirmPassword)).thenReturn(
                ResponseApp(
                        status = false,
                        statusCode = 400,
                        message = "email not valid",
                        data = null
                )
        )


        val result: Resource<ResponseApp<RegisterDto?>> =
                registerUseCase.invoke(username, wrongEmail, password, confirmPassword).filter { it is Resource.Error }
                        .first()

        assert(result is Resource.Error)
        val res = (result as Resource.Success).statusCode
        assert(res == 400)
    }

}