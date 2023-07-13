package com.andy.rios.moviesapp.domain.usecase

import com.andy.rios.moviesapp.domain.model.MovieEntity
import com.andy.rios.moviesapp.domain.repository.MovieRepository
import com.andy.rios.moviesapp.domain.util.ResultState

class GetMovieDetailUseCase (
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): ResultState<MovieEntity> = movieRepository.getMovie(movieId)
}