package com.example.mywaifu.data.sharedPrefs

import android.content.Context
import android.content.SharedPreferences
import com.example.mywaifu.data.sharedPrefs.client.StorageClient
import com.google.gson.Gson
import java.lang.reflect.Type

const val FAVORITE_WAIFU = "favorite"
const val ANOTHER_STORAGE = "another_storage"

class PrefsStorageClient<T>(
    private val context: Context,
    private val prefsFileName: String,
    private val dataKey: String,
    private val type: Type
) : StorageClient<T> {

    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFileName, Context.MODE_PRIVATE)
    private val gson = Gson()

    override fun write(data: T) {
        prefs.edit().putString(dataKey, gson.toJson(data, type)).apply()
    }

    override fun read(): T? {
        val dataJson = prefs.getString(dataKey, null)
        return  if (dataJson == null) {
            null
        } else {
            gson.fromJson(dataJson, type)
        }
    }

    override fun clear() {
        prefs.edit().remove(dataKey).apply()
    }


}
