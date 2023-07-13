package com.andy.rios.moviesapp.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.andy.rios.moviesapp.domain.repository.MovieRepository
import com.andy.rios.moviesapp.ui.mapper.toPresentationList
import com.andy.rios.moviesapp.ui.model.MovieList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun movies(pageSize: Int): Flow<PagingData<MovieList>> = movieRepository.movies(pageSize).map {
        it.map { movie -> movie.toPresentationList() }
    }
}