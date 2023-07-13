package com.andy.rios.moviesapp.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andy.rios.moviesapp.domain.usecase.SearchMovieUseCase
import com.andy.rios.moviesapp.ui.model.MovieList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MoviesSearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    companion object {
        const val KEY_QUERY = "query"
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val moviesSearch: Flow<PagingData<MovieList>> = savedStateHandle.getStateFlow(KEY_QUERY, "").flatMapLatest {
        searchMovieUseCase(it, 30).cachedIn(viewModelScope)
    }

    fun onSearchQuery(query: String) {
        savedStateHandle[KEY_QUERY] = query
    }
}