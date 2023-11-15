package com.ppidev.smartcube.data.remote.api

import android.util.Log
import com.ppidev.smartcube.contract.data.local.storage.ITokenAppDataStorePref
import com.ppidev.smartcube.data.local.entity.TokenAppEntity
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(private val tokenDataStorePref: ITokenAppDataStorePref<TokenAppEntity>) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val authToken = runBlocking {
            tokenDataStorePref.getAccessToken()
        }

        Log.d("TOKEN_ACCESS", authToken.toString())

        if (!authToken.isNullOrBlank()) {
            request.addHeader("Authorization", "Bearer $authToken")
        }

        return chain.proceed(request.build())
    }
}