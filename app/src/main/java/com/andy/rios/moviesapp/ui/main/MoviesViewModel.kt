package com.andy.rios.moviesapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andy.rios.moviesapp.common.State
import com.andy.rios.moviesapp.domain.usecase.GetMovieUseCase
import com.andy.rios.moviesapp.domain.usecase.SearchMovieUseCase
import com.andy.rios.moviesapp.ui.model.MovieList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase
    ): ViewModel() {

    data class FeedUiState(
        val showLoading: Boolean = true,
        val errorMessage: String? = null
    )

    private val _uiState: MutableStateFlow<FeedUiState> = MutableStateFlow(FeedUiState())
    val uiState = _uiState.asStateFlow()

    val movies: Flow<PagingData<MovieList>> = getMovieUseCase.movies(
        pageSize = 30
    ).cachedIn(viewModelScope)

}