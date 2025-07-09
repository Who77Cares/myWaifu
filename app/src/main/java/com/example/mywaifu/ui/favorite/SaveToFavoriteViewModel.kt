package com.example.mywaifu.ui.favorite

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mywaifu.App
import com.example.mywaifu.Creator
import com.example.mywaifu.domain.sharedPrefs.FavoriteInteractor

class SaveToFavoriteViewModel(context: Context): ViewModel() {

    private val favoriteInteractor by lazy {
        Creator.provideFavoriteInteractor(context)
    }


    companion object {
        fun getFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as App)
                SaveToFavoriteViewModel(app)
            }
        }
    }


    private val waifuListLiveData = MutableLiveData<MutableList<String>>()
    fun observeWaifuList(): LiveData<MutableList<String>> =  waifuListLiveData

    private val waifuList: MutableList<String> = mutableListOf()


    fun getWaifuList() {
        favoriteInteractor.readFavorite(
            object : FavoriteInteractor.FavoriteConsumer {
                override fun consume(favorite: MutableList<String>?) {
                    waifuList.addAll(favorite ?: mutableListOf())
                    waifuListLiveData.postValue(waifuList)
                }

            }
        )
    }

    fun clearWaifu() {
        favoriteInteractor.clearFavorite()
        waifuList.clear()
        waifuListLiveData.postValue(waifuList)

    }

}