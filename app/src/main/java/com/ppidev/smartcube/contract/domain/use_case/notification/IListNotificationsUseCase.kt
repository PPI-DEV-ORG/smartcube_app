package com.ppidev.smartcube.contract.domain.use_case.notification

import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.domain.model.NotificationModel
import kotlinx.coroutines.flow.Flow

interface IListNotificationsUseCase {
    operator fun invoke(): Flow<Resource<List<NotificationModel>>>
}