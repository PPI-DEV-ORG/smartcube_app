package com.ppidev.smartcube.data.local.storage

import android.content.Context
import android.content.SharedPreferences
import com.ppidev.smartcube.contract.data.local.storage.ISharedPreferencesManager

// Kelas implementasi SharedPreferences
class SharedPreferencesManager(context: Context) : ISharedPreferencesManager {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()


    override fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    override fun putString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    override fun putInt(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    override fun putBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    companion object {
        const val PREFERENCE_FILE_KEY = "preferences"
        const val PREFERENCE_KEY_FCM_TOKEN = "fcm_token"
    }
}
