package com.example.mywaifu.ui.fragments.view_models

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mywaifu.App
import com.example.mywaifu.Creator
import com.example.mywaifu.Resource
import com.example.mywaifu.domain.waifu_api.WaifuInteractor
import com.example.mywaifu.GlobalState

class OneWaifuViewModel(context: Context): ViewModel() {

    private val waifuInteractor = Creator.provideWifuInteractor()
    private val favoriteInteractor by lazy {
        Creator.provideFavoriteInteractor(context)
    }

    companion object {
        fun getFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as App)
                OneWaifuViewModel(app)
            }
        }
    }

    private var imgUrl: MutableList<String> = mutableListOf()

    private val stateLiveData = MutableLiveData<GlobalState>()
    fun observeState(): LiveData<GlobalState> = stateLiveData

    private val addToFavoriteLivedata = MutableLiveData<MutableList<String>>()
    fun observeAddToFavorite(): LiveData<MutableList<String>> = addToFavoriteLivedata


    private val toastLiveData = MutableLiveData<String>()
    fun observeToast(): LiveData<String> = toastLiveData

    private val handler = Handler(Looper.getMainLooper())


    fun getMyWaifu(type: String, category: String, singleImg: Boolean) {

        renderState(GlobalState.Loading)

        waifuInteractor.getWaifu(
            type = type,
            category = category,
            singleImg = singleImg,
            object : WaifuInteractor.WaifuConsumer {

                override fun consume(data: Any?, errorMessage: String?) {
                    handler.post {
                        if (data != null) {
                            if (singleImg) {
                                val url = data as String
                                renderState(GlobalState.Content(url))


                                imgUrl.add(0, url)
                                if (imgUrl.size > 3) imgUrl.removeAt(3)
//                                addToFavoriteLivedata.postValue(imgUrl)
                            }

                            if (!singleImg) {
                                val urlList = data as List<String>
                                renderState(GlobalState.ManyContent(urlList))
//                                imgUrl = urlList.toString()
//                                addToFavoriteLivedata.postValue(imgUrl)
                            }



                        } else if (errorMessage != null) {
                            renderState(GlobalState.Error(errorMessage))
                        }
                    }
                }

            },

        )
    }

    fun renderState(state: GlobalState) {
        stateLiveData.postValue(state)
    }

    fun addToFavorite() {
        val result = favoriteInteractor.validation(imgUrl[0])

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