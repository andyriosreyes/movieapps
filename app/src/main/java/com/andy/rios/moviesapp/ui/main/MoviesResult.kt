package com.andy.rios.moviesapp.ui.main

sealed class MoviesResult {

    object Loading : MoviesResult()

    data class Error(val errorMessage: String) : MoviesResult()

}