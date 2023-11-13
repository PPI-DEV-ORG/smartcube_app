package com.ppidev.smartcube.data.repository

import android.util.Log
import com.ppidev.smartcube.common.EExceptionCode
import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.contract.data.repository.IWeatherRepository
import com.ppidev.smartcube.data.remote.api.SmartCubeApi
import com.ppidev.smartcube.data.remote.api.WeatherApi
import com.ppidev.smartcube.data.remote.dto.WeatherDto
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : IWeatherRepository {
    override suspend fun getCurrentWeather(): ResponseApp<WeatherDto?> {
        try {
            val response = weatherApi.getCurrentWeather()
            if (!response.isSuccessful) {
                return ResponseApp(
                    status = false,
                    statusCode = response.code(),
                    message = response.message(),
                    data = null
                )
            }

            return ResponseApp(
                status = response.isSuccessful,
                statusCode = response.code(),
                message = response.message(),
                data = response.body()
            )
        } catch (e: Exception) {
            return ResponseApp(
                status = false,
                statusCode = EExceptionCode.RepositoryError.code,
                message = e.message ?: "Repository Error",
                data = null
            )
        }
    }
}