package com.ppidev.smartcube.domain.use_case.notification

import android.util.Log
import com.ppidev.smartcube.common.EExceptionCode
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.contract.data.repository.INotificationRepository
import com.ppidev.smartcube.contract.domain.use_case.notification.IListNotificationsUseCase
import com.ppidev.smartcube.data.remote.dto.toNotificationModel
import com.ppidev.smartcube.domain.model.NotificationModel
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ListNotificationsUseCase @Inject constructor(
    private val notificationRepository: Lazy<INotificationRepository>
) : IListNotificationsUseCase {
    override operator fun invoke(): Flow<Resource<List<NotificationModel>>> = flow {
        try {
            emit(Resource.Loading())
            val notificationsData =
                notificationRepository.get().getAllNotifications().data?.map { it.toNotificationModel() }

            emit(Resource.Success(notificationsData))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<NotificationModel>>(
                    EExceptionCode.IOException.ordinal,
                    e.localizedMessage ?: "An unexpected error occured"
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error<List<NotificationModel>>(
                    EExceptionCode.HTTPException.ordinal,
                    "Couldn't reach server. Check your internet connection."
                )
            )
        }
    }
}