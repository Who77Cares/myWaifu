package com.example.mywaifu.data.sharedPrefs.client

interface StorageClient<T> {

    fun write(data: T)
    fun read(): T?
    fun clear()

}
