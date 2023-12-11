package com.ppidev.smartcube.di

import com.ppidev.smartcube.contract.domain.use_case.auth.IChangePasswordUseCase
import com.ppidev.smartcube.contract.domain.use_case.auth.ILoginUseCase
import com.ppidev.smartcube.contract.domain.use_case.auth.IRegisterUseCase
import com.ppidev.smartcube.contract.domain.use_case.auth.IVerificationUseCase
import com.ppidev.smartcube.contract.domain.use_case.auth.IRequestLinkResetPasswordUseCase
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IAddEdgeDevicesUseCase
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IEdgeDevicesInfoUseCase
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IRestartEdgeDeviceUseCase
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IStartEdgeDeviceUseCase
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IUpdateEdgeDeviceUseCase
import com.ppidev.smartcube.contract.domain.use_case.edge_server.IAddEdgeServerUseCase
import com.ppidev.smartcube.contract.domain.use_case.edge_server.IListEdgeServerUseCase
import com.ppidev.smartcube.contract.domain.use_case.notification.IListNotificationUseCase
import com.ppidev.smartcube.contract.domain.use_case.notification.IViewNotificationUseCase
import com.ppidev.smartcube.contract.domain.use_case.user.IUpdateFcmTokenUseCase
import com.ppidev.smartcube.contract.domain.use_case.weather.IViewCurrentWeather
import com.ppidev.smartcube.domain.use_case.auth.ChangePasswordUseCase
import com.ppidev.smartcube.domain.use_case.auth.LoginUseCase
import com.ppidev.smartcube.domain.use_case.auth.RegisterUseCase
import com.ppidev.smartcube.domain.use_case.auth.VerificationUseCase
import com.ppidev.smartcube.domain.use_case.auth.RequestLinkResetPasswordUseCase
import com.ppidev.smartcube.domain.use_case.edge_device.AddEdgeDeviceUseCase
import com.ppidev.smartcube.domain.use_case.edge_device.EdgeDevicesInfoUseCase
import com.ppidev.smartcube.domain.use_case.edge_device.RestartEdgeEdgeDeviceUseCase
import com.ppidev.smartcube.domain.use_case.edge_device.StartEdgeEdgeDeviceUseCase
import com.ppidev.smartcube.domain.use_case.edge_device.UpdateEdgeDeviceUseCase
import com.ppidev.smartcube.domain.use_case.edge_server.AddEdgeServerUseCase
import com.ppidev.smartcube.domain.use_case.edge_server.ListEdgeServerUseCase
import com.ppidev.smartcube.domain.use_case.notification.ListNotificationUseCase
import com.ppidev.smartcube.domain.use_case.notification.ViewNotificationUseCase
import com.ppidev.smartcube.domain.use_case.user.UpdateFcmTokenUseCase
import com.ppidev.smartcube.domain.use_case.weather.ViewCurrentWeather
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    @Singleton
    abstract fun bindListNotificationsUseCase(
        listNotificationsUseCase: ListNotificationUseCase
    ): IListNotificationUseCase


    @Binds
    @Singleton
    abstract fun bindLoginUseCase(
        loginUseCase: LoginUseCase
    ): ILoginUseCase

    @Binds
    @Singleton
    abstract fun bindRegisterUseCase(
        registerUseCase: RegisterUseCase
    ): IRegisterUseCase


    @Binds
    @Singleton
    abstract fun bindVerificationUseCase(
        verificationUseCase: VerificationUseCase
    ): IVerificationUseCase

    @Binds
    @Singleton
    abstract fun bindRequestLinkResetPasswordUseCase(
        requestLinkResetPasswordUseCase: RequestLinkResetPasswordUseCase
    ): IRequestLinkResetPasswordUseCase

    @Binds
    @Singleton
    abstract fun bindChangePasswordUseCase(
        changePasswordUseCase: ChangePasswordUseCase
    ): IChangePasswordUseCase

    @Binds
    @Singleton
    abstract fun bindViewCurrentWeatherUseCase(
        viewCurrentWeather: ViewCurrentWeather
    ): IViewCurrentWeather

    @Binds
    @Singleton
    abstract fun bindViewNotificationUseCase(
        viewNotificationUseCase: ViewNotificationUseCase
    ): IViewNotificationUseCase

    @Binds
    @Singleton
    abstract fun bindAddEdgeServerUseCase(
        addEdgeServerUseCase: AddEdgeServerUseCase
    ): IAddEdgeServerUseCase

    @Binds
    @Singleton
    abstract fun bindListEdgeServerUseCase(
        listEdgeServerUseCase: ListEdgeServerUseCase
    ): IListEdgeServerUseCase

    @Binds
    @Singleton
    abstract fun bindEdgeDeviceInfoUseCase(
        edgeDevicesInfoUseCase: EdgeDevicesInfoUseCase
    ): IEdgeDevicesInfoUseCase

    @Binds
    @Singleton
    abstract fun bindAddEdgeDeviceUseCase(
        addEdgeDevicesUseCase: AddEdgeDeviceUseCase
    ): IAddEdgeDevicesUseCase

    @Binds
    @Singleton
    abstract fun bindStartEdgeDeviceUseCase(
        startEdgeDeviceUseCase: StartEdgeEdgeDeviceUseCase
    ): IStartEdgeDeviceUseCase

    @Binds
    @Singleton
    abstract fun bindRestartEdgeDeviceUseCase(
        restartEdgeDeviceUseCase: RestartEdgeEdgeDeviceUseCase
    ): IRestartEdgeDeviceUseCase

    @Binds
    @Singleton
    abstract fun bindUpdateEdgeDeviceUseCase(
        updateEdgeDeviceUseCase: UpdateEdgeDeviceUseCase
    ): IUpdateEdgeDeviceUseCase

    @Binds
    @Singleton
    abstract fun bindUpdateFcmTokenUseCase(
        updateFcmTokenUseCase: UpdateFcmTokenUseCase
    ): IUpdateFcmTokenUseCase
}