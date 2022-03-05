package com.learning.nasaapp.commonutils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object SharedPreferenceUtils {
    private const val PLANETARY_PREFS = "planet_prefs"
    private const val EMPTY_STRING = ""
    private var sharedPreference: SharedPreferences? = null

    fun getSharedPreferences(context: Context): SharedPreferenceUtils {
        if (sharedPreference == null) {
            sharedPreference = context.getSharedPreferences(PLANETARY_PREFS, MODE_PRIVATE)
        }
        return this
    }

    fun putString(key: String, value: String) {
        val editor = sharedPreference?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    fun getString(key: String): String? {
        return sharedPreference?.getString(key, EMPTY_STRING)
    }


}