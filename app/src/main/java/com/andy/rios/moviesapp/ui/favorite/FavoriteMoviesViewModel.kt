package com.andy.rios.moviesapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andy.rios.moviesapp.domain.usecase.GetFavoriteMoviesUseCase
import com.andy.rios.moviesapp.ui.model.MovieList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
) : ViewModel() {
    val moviesFavorite: Flow<PagingData<MovieList>> = getFavoriteMoviesUseCase(30).cachedIn(viewModelScope)
}