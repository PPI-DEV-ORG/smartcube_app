package com.ppidev.smartcube.domain.use_case.weather

import com.ppidev.smartcube.common.EExceptionCode
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.contract.data.repository.IWeatherRepository
import com.ppidev.smartcube.contract.domain.use_case.weather.IViewCurrentWeather
import com.ppidev.smartcube.data.remote.dto.WeatherDto
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ViewCurrentWeather @Inject constructor(
    private val weatherRepository: Lazy<IWeatherRepository>
) : IViewCurrentWeather {
    override fun invoke(): Flow<Resource<ResponseApp<WeatherDto?>>> = flow {
        try {

            emit(Resource.Loading())

            val response = weatherRepository.get().getCurrentWeather()

            if (!response.status) {
                emit(
                    Resource.Error(
                        response.statusCode,
                        response.message
                    )
                )

                return@flow
            }

            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    EExceptionCode.UseCaseError.code,
                    e.message ?: "Something wrong"
                )
            )
        }
    }
}