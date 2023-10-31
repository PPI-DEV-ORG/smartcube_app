package com.ppidev.smartcube.data.local.entity

import kotlinx.serialization.Serializable

@Serializable
data class AppSettingEntity(
    val theme: ETheme = ETheme.LIGHT,
    val language: ELanguage = ELanguage.ENGLISH,
    val selectedListenerIndexHostDevice: Int = 0
)

enum class ETheme {
    LIGHT,
    DARK
}
enum class ELanguage {
    ENGLISH,
    INDONESIA,
}