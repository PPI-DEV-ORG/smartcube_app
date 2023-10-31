package com.ppidev.smartcube.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

fun getBitmapFromUrl(imageUrl: String): Bitmap? {
    return try {
        val url = URL(imageUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input = connection.inputStream
        val bitmap = BitmapFactory.decodeStream(input)
        input.close()
        connection.disconnect()
        bitmap
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

fun validateEmail(email: String): Boolean {
    val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    return email.matches(emailRegex)
}