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
import java.util.regex.Pattern

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
    return Pattern.compile(
        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
    ).matcher(email).matches()
}

fun validatePassword(password: String): Boolean {
    return password.length >= 8
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
        val number = inputString.split(" ")[0].toLongOrNull()
        if (number == null) {
            println("Invalid input: $inputString does not contain a valid number.")
        }
        number
    } catch (e: NumberFormatException) {
        println("Invalid input: $inputString does not contain a valid number.")
        null
    }
}

fun extractFloatFromString(input: String): Float? {
    // Coba konversi langsung ke float
    val floatValue = input.toFloatOrNull()
    if (floatValue != null) {
        return floatValue
    }

    // Jika konversi langsung gagal, gunakan regex untuk mencari float dalam string
    val regex = Regex("""-?\d+(\.\d+)?""")
    val matchResult = regex.find(input)
    val extractedFloat = matchResult?.value?.toFloatOrNull()

    // Jika string input mengandung lebih dari satu tanda desimal, kembalikan null
    if (extractedFloat != null && input.count { it == '.' } > 1) {
        return null
    }

    return extractedFloat
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

fun isoDateToEpoch(isoDateString: String): Long? {
    return try {
        val instant = Instant.parse(isoDateString)
        instant.epochSecond
    } catch (e: Exception) {
        null
    }
}

inline fun <reified T> parseJson(json: String): T {
    return try {
        Gson().fromJson(json, object : TypeToken<T>() {}.type)
    } catch (e: JsonSyntaxException) {
        throw JsonParsingException("Failed to parse JSON", e)
    }
}

class JsonParsingException(message: String, cause: Throwable? = null) :
    RuntimeException(message, cause)
