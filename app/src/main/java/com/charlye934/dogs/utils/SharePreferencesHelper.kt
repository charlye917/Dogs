package com.charlye934.dogs.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class SharePreferencesHelper {
    companion object{
        private const val PREF_TIME = "Pref time"
        private var prefs: SharedPreferences? = null

        @Volatile private var instances: SharePreferencesHelper? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): SharePreferencesHelper = instances ?: synchronized(LOCK){
            instances ?: builderHelper(context).also{
                instances = it
            }
        }

        private fun builderHelper(context: Context): SharePreferencesHelper{
            prefs = context.getSharedPreferences("PREFERENCES_TIME",Context.MODE_PRIVATE)
            return SharePreferencesHelper()
        }
    }

    fun saveUpdateTime(time: Long){
        prefs?.edit()!!
                .putLong(PREF_TIME, time)
                .commit()
    }

    fun getUpdateTime() = prefs?.getLong(PREF_TIME, 0)

    fun getCacheDuration() = prefs?.getString("pref_cache_duration", "")
}