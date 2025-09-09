package com.example.mywaifu.ui.fragments.view_models

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
import com.example.mywaifu.domain.waifu_api.WaifuInteractor
import com.example.mywaifu.ui.main.MainState
import com.example.mywaifu.ui.main.MainViewModel

class TowWaifuViewModel(context: Context): ViewModel() {

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

    private val handler = Handler(Looper.getMainLooper())


    fun getManyWaifu(type: String, category: String, singleImg: Boolean) {
        waifuInteractor.getWaifu(
            type = type,
            category = category,
            singleImg = singleImg,
            object : WaifuInteractor.WaifuConsumer {
                override fun consume(data: Any?, errorMessage: String?) {
                    handler.post {
                        if(data != null) {
                            val urlList = data as List<String>
                            renderState(MainState.ManyContent(urlList))
                        } else {
                            renderState(MainState.Error("ошибка в getManyWaifu"))
                        }
                    }
                }

            }
        )

    }

    private val stateLiveData = MutableLiveData<MainState>()
    fun observeState(): LiveData<MainState> = stateLiveData


    fun renderState(state: MainState) {
        stateLiveData.postValue(state)
    }


}