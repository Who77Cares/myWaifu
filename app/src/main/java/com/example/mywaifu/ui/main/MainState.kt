package com.example.mywaifu.ui.main

sealed interface MainState {

    data object Loading : MainState
    data class Content(val url: String): MainState
    data class Error(val message: String): MainState

}