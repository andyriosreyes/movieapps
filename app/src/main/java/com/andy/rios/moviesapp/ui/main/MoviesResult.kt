package com.andy.rios.moviesapp.ui.main

sealed class MoviesResult {

    object Loading : MoviesResult()
    //data class Success(val categories: List<ServiceCategoryModel>) : MoviesResult()
    data class Success(val categories: String) : MoviesResult()
    data class Error(val errorMessage: String) : MoviesResult()

}