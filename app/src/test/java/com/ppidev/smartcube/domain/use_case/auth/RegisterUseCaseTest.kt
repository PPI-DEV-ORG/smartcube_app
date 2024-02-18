package com.ppidev.smartcube.domain.use_case.auth

import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.utils.ResponseApp
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

        val errorResponse = Resource.Error(
            message = "email not valid",
            statusCode = 400,
            data = null
        )


        `when`(authRepository.register(username, wrongEmail, password, confirmPassword)).thenReturn(
                ResponseApp(
                        status = false,
                        statusCode = 400,
                        message = "email not valid",
                        data = null
                )
        )

        val result =
                registerUseCase.invoke(username, wrongEmail, password, confirmPassword).filter { it is Resource.Error }
                        .first()

        assert(result is Resource.Error)
        assert(result.statusCode == errorResponse.statusCode)
    }


    @Test
    fun `test register success`() = runBlocking {
        val username = "testingUser"
        val email = "testing@gmail.com"
        val password = "freya123"
        val confirmPassword = "freya123"

        val expectedResponseDataRegister = RegisterDto(
            id = UInt.MIN_VALUE,
            username = "test",
            email = "testing@gmail.com",
            password = "",
            isVerified = false,
            verificationCode = "41kdnf"
        )

        `when`(authRepository.register(username, email, password, confirmPassword)).thenReturn(
            ResponseApp(
                status = true,
                statusCode = 200,
                message = "success register account",
                data = expectedResponseDataRegister
            )
        )

        val result : Resource<ResponseApp<RegisterDto?>> = registerUseCase.invoke(username, email, password, confirmPassword).filter { it is Resource.Success }.first()

        assert(result is Resource.Success)
        assert(result.data?.data == expectedResponseDataRegister)
    }
}