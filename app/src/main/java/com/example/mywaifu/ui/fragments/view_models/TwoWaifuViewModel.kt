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
import com.example.mywaifu.GlobalState
import com.example.mywaifu.ui.fragments.view_models.OneWaifuViewModel

class TwoWaifuViewModel(): ViewModel() {

    private val waifuInteractor = Creator.provideWifuInteractor()


    companion object {
        fun getFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                TwoWaifuViewModel()
            }
        }
    }

    private val handler = Handler(Looper.getMainLooper())


    fun getManyWaifu(type: String, category: String, singleImg: Boolean) {

        renderState(GlobalState.Loading)

        waifuInteractor.getWaifu(
            type = type,
            category = category,
            singleImg = singleImg,
            object : WaifuInteractor.WaifuConsumer {
                override fun consume(data: Any?, errorMessage: String?) {
                    handler.post {
                        if(data != null) {
                            val urlList = data as List<String>
                            renderState(GlobalState.ManyContent(urlList))
                        } else {
                            renderState(GlobalState.Error("ошибка в getManyWaifu"))
                        }
                    }
                }

            }
        )

    }

    private val stateLiveData = MutableLiveData<GlobalState>()
    fun observeState(): LiveData<GlobalState> = stateLiveData


    fun renderState(state: GlobalState) {
        stateLiveData.postValue(state)
    }


}