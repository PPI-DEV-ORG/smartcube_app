package com.ppidev.smartcube.domain.use_case.notification

import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.utils.ResponseApp
import com.ppidev.smartcube.contract.data.repository.INotificationRepository
import com.ppidev.smartcube.contract.domain.use_case.notification.IViewNotificationUseCase
import com.ppidev.smartcube.data.remote.dto.toNotificationModel
import com.ppidev.smartcube.domain.model.NotificationModel
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ViewNotificationUseCase @Inject constructor(
    private val notificationRepository: Lazy<INotificationRepository>
) : IViewNotificationUseCase {
    override fun invoke(notificationId: UInt, edgeServerId: UInt): Flow<Resource<ResponseApp<NotificationModel?>>> =
        flow {
            emit(Resource.Loading())
            emit(getDetailNotification(notificationId, edgeServerId))
        }

    private suspend fun getDetailNotification(notificationId: UInt, edgeServerId: UInt): Resource<ResponseApp<NotificationModel?>> {
        return try {
            val notificationResponse =
                notificationRepository.get().getDetailNotification(notificationId = notificationId, edgeServerId = edgeServerId)
            if (!notificationResponse.status) {
                return Resource.Error<ResponseApp<NotificationModel?>>(
                    notificationResponse.statusCode, notificationResponse.message
                )
            }

            val result: ResponseApp<NotificationModel?> = ResponseApp(
                status = notificationResponse.status,
                statusCode = notificationResponse.statusCode,
                message = notificationResponse.message,
                data = notificationResponse.data?.toNotificationModel()
            )

            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error<ResponseApp<NotificationModel?>>(
                EExceptionCode.HTTPException.ordinal, e.message ?: "Something wrong"
            )
        }
    }
}