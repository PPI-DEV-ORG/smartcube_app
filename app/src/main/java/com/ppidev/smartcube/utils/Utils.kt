package com.ppidev.smartcube.utils

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.TimeZone

fun urlToBitmap(imageUrl: String): Bitmap? {
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

fun getFloatFromPercentageString(percentage: String): Float {
    return try {
        val cleanedPercentage = percentage.replace("%", "").trim()
        cleanedPercentage.toFloat()
    } catch (e: NumberFormatException) {
        0f
    }
}

fun convertMillisecondsToHoursAndMinutes(milliseconds: Long): String {
    val seconds = milliseconds / 1000
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    return String.format("%02d hours %02d minutes", hours, minutes)
}

fun extractNumberFromString(inputString: String): Long? {
    return try {
        val number = inputString.split(" ")[0].toDouble().toLong()
        number
    } catch (e: NumberFormatException) {
        println("Invalid input: $inputString does not contain a valid number.")
        null
    }
}

fun extractFloatFromString(input: String): Float? {
    val regex = Regex("""\d+\.\d+""")
    val matchResult = regex.find(input)
    return matchResult?.value?.toFloatOrNull()
}

@SuppressLint("SimpleDateFormat")
fun isoDateFormatToStringDate(isoDate: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        inputFormat.timeZone = TimeZone.getDefault()
        val date = inputFormat.parse(isoDate) as Date
        val outputFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        outputFormat.timeZone = TimeZone.getDefault()
        outputFormat.format(date)
    } catch (e: Exception) {
        "-"
    }
}

fun isoDateToEpoch(isoDateString: String): Long {
    val instant = Instant.parse(isoDateString)
    return instant.epochSecond
}

inline fun <reified T> parseJson(json: String): T {
    return try {
        Gson().fromJson(json, object: TypeToken<T>() {}.type)
    } catch (e: JsonSyntaxException) {
        throw JsonParsingException("Failed to parse JSON", e)
    }
}

class JsonParsingException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)
