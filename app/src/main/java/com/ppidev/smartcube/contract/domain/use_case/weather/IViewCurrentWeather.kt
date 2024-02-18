package com.ppidev.smartcube.contract.domain.use_case.weather

import com.ppidev.smartcube.data.remote.dto.WeatherDto
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.utils.ResponseApp
import kotlinx.coroutines.flow.Flow

interface IViewCurrentWeather {
    operator fun invoke(): Flow<Resource<ResponseApp<WeatherDto?>>>
}