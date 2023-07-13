package com.andy.rios.moviesapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.andy.rios.moviesapp.domain.model.MovieEntity
import com.andy.rios.moviesapp.domain.usecase.AddMovieToFavoriteUseCase
import com.andy.rios.moviesapp.domain.usecase.CheckFavoriteStatusUseCase
import com.andy.rios.moviesapp.domain.usecase.GetMovieDetailUseCase
import com.andy.rios.moviesapp.domain.usecase.RemoveMovieFromFavoriteUseCase
import com.andy.rios.moviesapp.domain.util.ResultState
import com.andy.rios.moviesapp.domain.util.getResult
import com.andy.rios.moviesapp.domain.util.onSuccess
import com.andy.rios.moviesapp.ui.model.MovieList
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class MovieDetailViewModel @AssistedInject constructor(
    @Assisted private var movieId: Int,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val checkFavoriteStatusUseCase: CheckFavoriteStatusUseCase,
    private val addMovieToFavoriteUseCase: AddMovieToFavoriteUseCase,
    private val removeMovieFromFavoriteUseCase: RemoveMovieFromFavoriteUseCase
        ) : ViewModel() {

    init {
        onGetDetail(movieId)
    }

    data class MovieDetailsUiState(
        val title: String? = "",
        val overview: String? = "",
        val backdrop_path: String? = "",
        val isFavorite: Boolean = false,
    )

    private val _uiState: MutableStateFlow<MovieDetailsUiState> = MutableStateFlow(MovieDetailsUiState())
    val uiState = _uiState.asStateFlow()

    private suspend fun checkFavoriteStatus(movieId: Int): ResultState<Boolean> = checkFavoriteStatusUseCase.invoke(movieId)

    private suspend fun getMovieById(movieId: Int): ResultState<MovieEntity> = getMovieDetailUseCase(movieId)

    fun onGetDetail(movieId: Int) = viewModelScope.launch {
        val isFavorite = async { checkFavoriteStatus(movieId).getResult({ favoriteResult -> favoriteResult.data }, { false }) }
        getMovieById(movieId).onSuccess {
            _uiState.value = MovieDetailsUiState(
                title = it.title,
                overview = it.overview,
                backdrop_path = it.backdrop_path,
                isFavorite = isFavorite.await()
            )
        }
    }

    fun onFavoriteClicked() = viewModelScope.launch {
        checkFavoriteStatus(movieId).onSuccess { isFavorite ->
            if (isFavorite) removeMovieFromFavoriteUseCase(movieId) else addMovieToFavoriteUseCase(movieId)
            _uiState.update { it.copy(isFavorite = !isFavorite) }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(movieId: Int): MovieDetailViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            movieId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T = assistedFactory.create(movieId) as T
        }
    }
}