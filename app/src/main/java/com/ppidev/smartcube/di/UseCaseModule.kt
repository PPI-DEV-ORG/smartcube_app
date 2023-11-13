package com.ppidev.smartcube.di

import com.ppidev.smartcube.contract.domain.use_case.auth.IChangePasswordUseCase
import com.ppidev.smartcube.contract.domain.use_case.auth.ILoginUseCase
import com.ppidev.smartcube.contract.domain.use_case.auth.IRegisterUseCase
import com.ppidev.smartcube.contract.domain.use_case.auth.IRequestLinkResetPasswordUseCase
import com.ppidev.smartcube.contract.domain.use_case.notification.IListNotificationsUseCase
import com.ppidev.smartcube.contract.domain.use_case.weather.IViewCurrentWeather
import com.ppidev.smartcube.domain.use_case.auth.ChangePasswordUseCase
import com.ppidev.smartcube.domain.use_case.auth.LoginUseCase
import com.ppidev.smartcube.domain.use_case.auth.RegisterUseCase
import com.ppidev.smartcube.domain.use_case.auth.RequestLinkResetPasswordUseCase
import com.ppidev.smartcube.domain.use_case.notification.ListNotificationsUseCase
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
        listNotificationsUseCase: ListNotificationsUseCase
    ): IListNotificationsUseCase


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
}