package com.example.mywaifu

import android.util.Log
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

object WaifuProxy {

    private val proxies = listOf(
        "https://images.weserv.nl/?url=",
        "https://corsproxy.io/?",
        "https://api.allorigins.win/raw?url=",
        "https://cors-anywhere.herokuapp.com/",
        "https://thingproxy.freeboard.io/fetch/",
        "https://crossorigin.me/",
        "https://api.codetabs.com/v1/proxy?quest=",
        "https://yacdn.org/proxy/",
        "https://proxy.cors.sh/",
        "https://cors-proxy.htmldriven.com/?url="
    )

    // Альтернативные методы
    private val imageProxyServices = listOf(
        "https://images.weserv.nl/?url=",
        "https://images-proxy.herokuapp.com/",
        "https://pic.re/image?url=",
        "https://api.microlink.io/?url=",
        "https://img.poi.download/?url="
    )

    fun getProxiedUrl(originalUrl: String): String {
        Log.d("WaifuProxy", "Получение прокси для: $originalUrl")

        // Пробуем обычные CORS прокси
        for (proxy in proxies) {
            val proxiedUrl = proxy + URLEncoder.encode(originalUrl, "UTF-8")
            Log.d("WaifuProxy", "Пробуем прокси: $proxy")
            if (testProxy(proxiedUrl)) {
                return proxiedUrl
            }
        }

        // Пробуем сервисы для изображений
        for (imageProxy in imageProxyServices) {
            val proxiedUrl = imageProxy + URLEncoder.encode(originalUrl, "UTF-8")
            Log.d("WaifuProxy", "Пробуем image proxy: $imageProxy")
            if (testProxy(proxiedUrl)) {
                return proxiedUrl
            }
        }

        // Fallback: возвращаем оригинальный URL
        return originalUrl
    }

    private fun testProxy(proxiedUrl: String): Boolean {
        return try {
            val connection = URL(proxiedUrl).openConnection() as HttpURLConnection
            connection.connectTimeout = 3000
            connection.readTimeout = 3000
            connection.requestMethod = "HEAD"
            connection.setRequestProperty("User-Agent", "Mozilla/5.0")

            val responseCode = connection.responseCode
            connection.disconnect()

            responseCode == 200 || responseCode == 302 || responseCode == 301
        } catch (e: Exception) {
            false
        }
    }
}