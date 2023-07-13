package com.andy.rios.moviesapp.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.andy.rios.moviesapp.domain.repository.MovieRepository
import com.andy.rios.moviesapp.ui.mapper.toPresentationList
import com.andy.rios.moviesapp.ui.model.MovieList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavoriteMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(pageSize: Int): Flow<PagingData<MovieList>> =
        movieRepository.favoriteMovies(pageSize).map {
            it.map { movie -> movie.toPresentationList() }
        }
}