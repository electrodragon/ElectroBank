package com.electrodragon.electrobank.utils.prefs_controller

import android.content.SharedPreferences
import javax.inject.Inject

class PrefsController @Inject constructor(private val preferences: SharedPreferences) {

    fun saveUserId(id: Int) {
        preferences.edit()
            .putInt(PrefsKeys.ID.name, id)
            .apply()
    }

    fun getUserId(): Int? {
        if (preferences.contains(PrefsKeys.ID.name)) {
            return preferences.getInt(PrefsKeys.ID.name, -1)
        }
        return null
    }

    fun removeUserId() {
        preferences.edit().remove(PrefsKeys.ID.name).apply()
    }

    enum class PrefsKeys {
        ID
    }

}