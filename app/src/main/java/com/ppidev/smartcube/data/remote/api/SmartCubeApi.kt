package com.ppidev.smartcube.data.remote.api

import com.ppidev.smartcube.BuildConfig
import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.data.remote.dto.CreateEdgeDeviceDto
import com.ppidev.smartcube.data.remote.dto.CreateEdgeServerDto
import com.ppidev.smartcube.data.remote.dto.EdgeDevicesInfoDto
import com.ppidev.smartcube.data.remote.dto.EdgeServerItemDto
import com.ppidev.smartcube.data.remote.dto.LoginDto
import com.ppidev.smartcube.data.remote.dto.NotificationDto
import com.ppidev.smartcube.data.remote.dto.RegisterDto
import com.ppidev.smartcube.data.remote.dto.VerificationDto
import com.ppidev.smartcube.data.remote.dto.WeatherDto
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface SmartCubeApi : NotificationApi, AuthApi, EdgeServerApi, EdgeDeviceApi

interface NotificationApi {
    @GET("notification")
    suspend fun getListNotifications(): Response<ResponseApp<List<NotificationDto>?>>

    @GET("notification/{notificationId}")
    suspend fun getListNotificationById(
        @Path("notificationId") notificationId: UInt
    ): Response<ResponseApp<NotificationDto?>>
}

interface AuthApi {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<ResponseApp<LoginDto?>>

    @FormUrlEncoded
    @POST("signup")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("cPassword") confirmPassword: String
    ): Response<ResponseApp<RegisterDto?>>

    @FormUrlEncoded
    @POST("verification")
    suspend fun verification(
        @Field("email") email: String,
        @Field("verificationCode") verificationCode: String
    ): Response<ResponseApp<VerificationDto?>>

    @FormUrlEncoded
    @POST("reset-password-request")
    suspend fun resetToken(
        @Field("email") email: String
    ): Response<ResponseApp<String?>>

    @FormUrlEncoded
    @POST("reset-password")
    suspend fun changePassword(
        @Header("Authorization") authorizationHeader: String,
        @Field("password") password: String,
        @Field("cPassword") confirmationPassword: String
    ): Response<ResponseApp<Boolean?>>
}

interface EdgeServerApi {
    @FormUrlEncoded
    @POST("edge-server")
    suspend fun createEdgeServer(
        @Field("name") name: String,
        @Field("vendor") vendor: String,
        @Field("description") description: String
    ): Response<ResponseApp<CreateEdgeServerDto?>>

    @GET("edge-server")
    suspend fun getListEdgeServer(): Response<ResponseApp<List<EdgeServerItemDto>>>

}


interface WeatherApi {
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("q") q: String = "auto:ip",
        @Query("lang") lang: String = "en",
        @Query("key") apiKey: String = BuildConfig.WEATHER_API_KEY
    ): Response<WeatherDto?>
}

interface EdgeDeviceApi {
    @FormUrlEncoded
    @POST("edge-device")
    suspend fun createEdgeDevice(
        @Field("edge_server_id") edgeServerId: UInt,
        @Field("vendor_name") vendorName: String,
        @Field("vendor_number") vendorNumber: String,
        @Field("type") type: String,
        @Field("source_type") sourceType: String,
        @Field("source_address") sourceAddress: String,
        @Field("additional_info") additionalInfo: String,
        @Field("assigned_model_type") assignedModelType: UInt,
        @Field("assigned_model_index") assignedModelIndex: UInt
    ): Response<ResponseApp<CreateEdgeDeviceDto?>>


    @GET("edge-device/{edgeServerId}")
    suspend fun getEdgeDevices(
        @Path("edgeServerId") edgeServerId: UInt
    ): Response<ResponseApp<EdgeDevicesInfoDto?>>

    @GET("edge-device/{edgeDeviceId}")
    suspend fun getEdgeDevicesById(
        @Path("edgeDeviceId") edgeDeviceId: UInt
    )

    @FormUrlEncoded
    @PUT("edge-device/{edgeDeviceId}")
    suspend fun updateEdgeDevice(
        @Path("edgeDeviceId") edgeDeviceId: UInt,
        @Field("edge_server_id") edgeServerId: UInt,
        @Field("vendor_name") vendorName: String,
        @Field("vendor_number") vendorNumber: String,
        @Field("type") type: String,
        @Field("source_type") sourceType: String,
        @Field("source_address") devSourceId: String,
        @Field("assigned_model_type") assignedModelType: UInt,
        @Field("assigned_model_index") assignedModelIndex: UInt,
        @Field("additional_info") additionalInfo: String
    ): Response<ResponseApp<Any>>

    @FormUrlEncoded
    @POST("edge-device-restart")
    suspend fun restartEdgeDevice(
        @Field("edge_server_id") edgeServerId: UInt,
        @Field("process_index") processIndex: Int
    ): Response<ResponseApp<Any>>

    @FormUrlEncoded
    @POST("edge-device-start")
    suspend fun startEdgeDevice(
        @Field("edge_server_id") edgeServerId: UInt,
        @Field("process_index") processIndex: Int
    ): Response<ResponseApp<Any>>
}