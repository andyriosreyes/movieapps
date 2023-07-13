package com.andy.rios.moviesapp.data.datasource.movies

import androidx.paging.PagingSource
import com.andy.rios.moviesapp.ui.model.database.movies.MovieDao
import com.andy.rios.moviesapp.ui.model.database.movies.MoviePageDao
import com.andy.rios.moviesapp.data.exception.DataNotAvailableException
import com.andy.rios.moviesapp.data.mapper.toDbData
import com.andy.rios.moviesapp.data.mapper.toDomain
import com.andy.rios.moviesapp.data.model.movies.MovieDbData
import com.andy.rios.moviesapp.data.model.movies.MoviePageDbData
import com.andy.rios.moviesapp.domain.model.MovieEntity
import kotlinx.coroutines.withContext
import com.andy.rios.moviesapp.domain.util.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher

class MovieLocalDataSource(
    private val movieDao: MovieDao,
    private val remoteKeyDao: MoviePageDao,
) : MovieDataSource.Local {

    override fun movies(): PagingSource<Int, MovieDbData> = movieDao.movies()

    override suspend fun getMovies(): ResultState<List<MovieEntity>> =
        withContext(Dispatchers.IO) {
            val movies = movieDao.getMovies()
            return@withContext if (movies.isNotEmpty()) {
                ResultState.Success(movies.map { it.toDomain() })
            } else {
                ResultState.Error(DataNotAvailableException())
            }
        }

    override suspend fun getMovie(movieId: Int): ResultState<MovieEntity> =
        withContext(Dispatchers.IO) {
            return@withContext movieDao.getMovie(movieId)?.let {
                ResultState.Success(it.toDomain())
            } ?: ResultState.Error(DataNotAvailableException())
        }

    override suspend fun saveMovies(movieEntities: List<MovieEntity>) =
        withContext(Dispatchers.IO) {
            movieDao.saveMovies(movieEntities.map { it.toDbData() })
        }

    override suspend fun getLastPage(): MoviePageDbData? =
        withContext(Dispatchers.IO) {
            remoteKeyDao.getLastPage()
        }

    override suspend fun savePage(key: MoviePageDbData) =
        withContext(Dispatchers.IO) {
            remoteKeyDao.savePage(key)
        }

    override suspend fun clearMovies() = withContext(Dispatchers.IO) {
        movieDao.clearMoviesExceptFavorites()
    }

    override suspend fun clearPage() = withContext(Dispatchers.IO) {
        remoteKeyDao.clearPage()
    }
}