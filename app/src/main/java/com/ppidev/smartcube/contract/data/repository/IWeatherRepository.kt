package com.ppidev.smartcube.contract.data.repository

import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.data.remote.dto.WeatherDto

interface IWeatherRepository {
    suspend fun getCurrentWeather(): ResponseApp<WeatherDto?>
}