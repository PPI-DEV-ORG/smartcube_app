package com.ppidev.smartcube.contract.data.repository

import com.ppidev.smartcube.data.remote.dto.WeatherDto
import com.ppidev.smartcube.utils.ResponseApp

interface IWeatherRepository {
    suspend fun getCurrentWeather(): ResponseApp<WeatherDto?>
}