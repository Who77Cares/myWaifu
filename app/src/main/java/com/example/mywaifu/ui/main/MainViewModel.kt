package com.example.mywaifu.ui.main

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mywaifu.App
import com.example.mywaifu.Creator
import com.example.mywaifu.Resource
import com.example.mywaifu.domain.waifu_api.WaifuInteractor

class MainViewModel(context: Context): ViewModel() {

    private val waifuInteractor = Creator.provideWifuInteractor()
    private val favoriteInteractor by lazy {
        Creator.provideFavoriteInteractor(context)
    }

    companion object {
        fun getFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as App)
                MainViewModel(app)
            }
        }
    }

    private var imgUrl: String = ""

    private val stateLiveData = MutableLiveData<MainState>()
    fun observeState(): LiveData<MainState> = stateLiveData

    private val addToFavoriteLivedata = MutableLiveData<String>()
    fun observeAddToFavorite(): LiveData<String> = addToFavoriteLivedata


    private val toastLiveData = MutableLiveData<String>()
    fun observeToast(): LiveData<String> = toastLiveData

    private val handler = Handler(Looper.getMainLooper())


    fun getMyWaifu(type: String, category: String) {

        renderState(MainState.Loading)

        waifuInteractor.getWaifu(
            type = type,
            category = category,
            object : WaifuInteractor.WaifuConsumer {

                override fun consume(url: String?, errorMessage: String?) {
                    handler.post {

                        if (url != null) {
                            renderState(MainState.Content(url))
                            imgUrl = url

                        } else if (errorMessage != null) {
                            renderState(MainState.Error(errorMessage))
                        }
                    }
                }

            }
        )
    }

    fun renderState(state: MainState) {
        stateLiveData.postValue(state)
    }

    fun addToFavorite() {
        val result = favoriteInteractor.validation(imgUrl)

        when (result) {
            is Resource.Error -> {
                toastLiveData.postValue(result.message)
            }

            is Resource.Success -> {
                toastLiveData.postValue(result.data)
                addToFavoriteLivedata.postValue(imgUrl)
            }
        }
    }


}