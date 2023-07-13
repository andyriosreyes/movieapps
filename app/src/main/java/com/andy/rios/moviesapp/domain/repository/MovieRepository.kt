package com.andy.rios.moviesapp.domain.repository

import androidx.paging.PagingData
import com.andy.rios.moviesapp.common.Either
import com.andy.rios.moviesapp.domain.model.MovieEntity
import kotlinx.coroutines.flow.Flow
import com.andy.rios.moviesapp.domain.util.ResultState

interface MovieRepository {
    fun movies(pageSize: Int): Flow<PagingData<MovieEntity>>
    fun favoriteMovies(pageSize: Int): Flow<PagingData<MovieEntity>>
    fun search(query: String, pageSize: Int): Flow<PagingData<MovieEntity>>
    suspend fun getMovie(movieId: Int): ResultState<MovieEntity>
    suspend fun checkFavoriteStatus(movieId: Int): ResultState<Boolean>
    suspend fun addMovieToFavorite(movieId: Int)
    suspend fun removeMovieFromFavorite(movieId: Int)
//    suspend fun sync(): Boolean
}