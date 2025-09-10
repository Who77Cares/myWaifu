package com.example.mywaifu

sealed interface GlobalState {

    data object Loading : GlobalState
    data class Content(val url: String): GlobalState
    data class ManyContent(val manyUrl: List<String>): GlobalState
    data class Error(val message: String): GlobalState

}