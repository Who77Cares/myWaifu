package com.example.mywaifu.data.sharedPrefs

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

const val FAVORITE = "favorite"

class PrefsStorageClient {

    fun read(prefs: SharedPreferences): List<String>? {
        val json = prefs.getString(FAVORITE, "")
        return Gson().fromJson(json, object : TypeToken<MutableList<String>>() {}.type)
    }

    fun write(prefs: SharedPreferences,
              favorite: List<String>,
              key: String
    ) {
        val json = Gson().toJson(favorite)
        prefs.edit()
            .putString(key, json)
            .apply()
    }

    fun clear(prefs: SharedPreferences) {
        prefs.edit().clear().apply()
    }

}