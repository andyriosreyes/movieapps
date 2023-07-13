package com.andy.rios.moviesapp.domain.usecase

import com.andy.rios.moviesapp.domain.repository.MovieRepository

class AddMovieToFavoriteUseCase (
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) = movieRepository.addMovieToFavorite(movieId)
}