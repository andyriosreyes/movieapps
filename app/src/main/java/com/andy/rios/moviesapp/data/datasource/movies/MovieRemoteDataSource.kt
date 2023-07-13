package com.andy.rios.moviesapp.data.datasource.movies

import com.andy.rios.moviesapp.data.api.MovieApi
import com.andy.rios.moviesapp.data.mapper.toDomain
import com.andy.rios.moviesapp.domain.model.MovieEntity
import com.andy.rios.moviesapp.domain.util.ResultState

class MovieRemoteDataSource(
    private val movieApi: MovieApi
) : MovieDataSource.Remote {

    override suspend fun getMovies(page: Int, limit: Int): ResultState<List<MovieEntity>> = try {
        val result = movieApi.getMovies(page)
        ResultState.Success(result.results.map { it.toDomain() })
    } catch (e: Exception) {
        ResultState.Error(e)
    }

    override suspend fun getMovie(movieId: Int): ResultState<MovieEntity> = try {
        val result = movieApi.getMovie(movieId)
        ResultState.Success(result.toDomain())
    } catch (e: Exception) {
        ResultState.Error(e)
    }

    override suspend fun search(query: String, page: Int, limit: Int): ResultState<List<MovieEntity>> = try {
        val result = movieApi.search(query, page)
        ResultState.Success(result.results.map { it.toDomain() })
    } catch (e: Exception) {
        ResultState.Error(e)
    }
}