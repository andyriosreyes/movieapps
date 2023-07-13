package com.andy.rios.moviesapp.domain.usecase

import com.andy.rios.moviesapp.domain.repository.MovieRepository
import com.andy.rios.moviesapp.domain.util.ResultState
import javax.inject.Inject

class CheckFavoriteStatusUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): ResultState<Boolean> = movieRepository.checkFavoriteStatus(movieId)
}