package com.ppidev.smartcube.data.local.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class AppSettingModel(
    val permitNotification: Boolean = true,
    val typeNotification: ETypeNotification = ETypeNotification.SOUND_VIBRATE,
    val theme: ETheme = ETheme.LIGHT,
    val language: ELanguage = ELanguage.ENGLISH
)

enum class ETypeNotification {
    SOUND_VIBRATE,
    VIBRATE,
    SOUND
}

enum class ETheme {
    LIGHT,
    DARK
}

enum class ELanguage {
    ENGLISH,
    INDONESIA,
}