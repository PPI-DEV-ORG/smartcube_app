package com.ppidev.smartcube.domain.use_case.auth

import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.utils.ResponseApp
import com.ppidev.smartcube.contract.data.repository.IAuthRepository
import com.ppidev.smartcube.data.remote.dto.LoginDto
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


class LoginUseCaseTest {
    private lateinit var loginUseCase: LoginUseCase

    @Mock
    private var authRepository = mock<IAuthRepository>()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        val lazy = Lazy { authRepository }
        val context: Context = getApplicationContext()

        loginUseCase = LoginUseCase(lazy, context)
    }

    @Test
    fun `test login success`() = runBlocking {
        val email = "test@example.com"
        val password = "password"
        val expectedLoginDto: LoginDto = LoginDto(
            accessToken = "myToken"
        )

        // Mock the loginResponse from authRepository
        `when`(authRepository.login(email, password)).thenReturn(
            ResponseApp(
                statusCode = 200,
                status = true,
                message = "login success",
                data = expectedLoginDto
            )
        )

        val result: Resource<LoginDto?, Any> =
            loginUseCase.invoke(email, password).filter { it is Resource.Success }
                .first()

        assert(result is Resource.Success)
        val res = (result as Resource.Success).data
        assert(res == expectedLoginDto)
    }

    @Test
    fun `test login failure`() = runBlocking {
        val email = "test@example.com"
        val password = "password"

        val errorResponse = Resource.Error(
            message = "login failed",
            statusCode = 400,
            data = null
        )

        // Mock the loginResponse from authRepository
        `when`(authRepository.login(email, password)).thenReturn(
            ResponseApp(
            statusCode = 400,
            status = false,
            message = "login failed",
            data = null
        )
        )

        val result = loginUseCase.invoke(email, password).filter { it is Resource.Error }
            .first()

        assert(result is Resource.Error)
    }
}