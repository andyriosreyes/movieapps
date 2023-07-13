package com.andy.rios.moviesapp.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.andy.rios.moviesapp.domain.repository.MovieRepository
import com.andy.rios.moviesapp.ui.mapper.toPresentationList
import com.andy.rios.moviesapp.ui.model.MovieList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchMovieUseCase(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(query: String, pageSize: Int): Flow<PagingData<MovieList>> = movieRepository.search(query, pageSize).map {
        it.map { movie -> movie.toPresentationList() }
    }
}
