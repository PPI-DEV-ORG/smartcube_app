package com.ppidev.smartcube.domain.use_case.weather

import com.ppidev.smartcube.contract.data.repository.IWeatherRepository
import com.ppidev.smartcube.contract.domain.use_case.weather.IViewCurrentWeather
import com.ppidev.smartcube.data.remote.dto.WeatherDto
import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ViewCurrentWeather @Inject constructor(
    private val weatherRepository: Lazy<IWeatherRepository>
) : IViewCurrentWeather {
    override fun invoke(): Flow<Resource<WeatherDto?, Any>> = flow {
        emit(Resource.Loading())
        emit(getCurrentWeather())
    }

    private suspend fun getCurrentWeather(): Resource<WeatherDto?, Any> {
        try {
            val response = weatherRepository.get().getCurrentWeather()

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