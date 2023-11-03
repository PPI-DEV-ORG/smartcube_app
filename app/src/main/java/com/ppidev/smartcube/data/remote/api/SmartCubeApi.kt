package com.ppidev.smartcube.data.remote.api

import com.ppidev.smartcube.common.Response
import com.ppidev.smartcube.data.remote.dto.LoginDto
import com.ppidev.smartcube.data.remote.dto.NotificationDto
import com.ppidev.smartcube.data.remote.dto.RegisterDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SmartCubeApi : NotificationApi, AuthApi {
}

interface NotificationApi {
    @GET("notification")
    suspend fun getListNotifications(): Response<List<NotificationDto>>

    @GET("notification/{notificationId}")
    suspend fun getListNotificationById(
        @Path("notificationId") notificationId: Int
    ): Response<NotificationDto>
}

interface AuthApi {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginDto?>

    @FormUrlEncoded
    @POST("signup")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("cPassword") confirmPassword: String
    ): Response<RegisterDto?>
}