package com.andy.rios.moviesapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andy.rios.moviesapp.domain.usecase.GetMovieUseCase
import com.andy.rios.moviesapp.ui.model.MovieList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase
    ): ViewModel() {

    val movies: Flow<PagingData<MovieList>> = getMovieUseCase.movies(
        pageSize = 30
    ).cachedIn(viewModelScope)

}