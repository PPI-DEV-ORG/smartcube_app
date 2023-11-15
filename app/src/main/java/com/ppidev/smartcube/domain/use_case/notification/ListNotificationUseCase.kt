package com.ppidev.smartcube.domain.use_case.notification

import com.ppidev.smartcube.common.EExceptionCode
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.contract.data.repository.INotificationRepository
import com.ppidev.smartcube.contract.domain.use_case.notification.IListNotificationUseCase
import com.ppidev.smartcube.data.remote.dto.toNotificationModel
import com.ppidev.smartcube.domain.model.NotificationModel
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListNotificationUseCase @Inject constructor(
    private val notificationRepository: Lazy<INotificationRepository>
) : IListNotificationUseCase {
    override operator fun invoke(): Flow<Resource<List<NotificationModel>>> = flow {
        emit(Resource.Loading())
        emit(getListNotifications())
    }

    private suspend fun getListNotifications(): Resource<List<NotificationModel>> {
        return try {
            val notificationsData = notificationRepository.get()
                .getAllNotifications().data?.map { it.toNotificationModel() }

            Resource.Success(notificationsData)
        } catch (e: Exception) {
            Resource.Error<List<NotificationModel>>(
                EExceptionCode.HTTPException.ordinal, e.message ?: "Something wrong"
            )
        }
    }
}