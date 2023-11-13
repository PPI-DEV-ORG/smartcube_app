package com.ppidev.smartcube.contract.domain.use_case.weather

import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.data.remote.dto.WeatherDto
import kotlinx.coroutines.flow.Flow

interface IViewCurrentWeather {
    operator fun invoke(): Flow<Resource<ResponseApp<WeatherDto?>>>
}