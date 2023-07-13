package com.andy.rios.moviesapp.ui.main

import android.app.appsearch.SearchResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andy.rios.moviesapp.common.State
import com.andy.rios.moviesapp.domain.usecase.SearchMovieUseCase
import com.andy.rios.moviesapp.ui.model.MovieList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.switchMap
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MoviesSearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val t = MutableLiveData("")

    val moviesSearch = t.switchMap { query ->
        searchMovieUseCase(query, 30).cachedIn(viewModelScope).asLiveData()
    }

    data class SearchUiState(
        val pagingData: MovieList = MovieList(),
        val showLoading: Boolean = false,
        val showNoMoviesFound: Boolean = false,
        val errorMessage: String? = null
    )

    fun onSearchQuery(query: String){
        t.value = query
    }
}