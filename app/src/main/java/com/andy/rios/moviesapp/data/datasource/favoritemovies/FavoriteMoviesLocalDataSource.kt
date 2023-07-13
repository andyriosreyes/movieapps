package com.andy.rios.moviesapp.data.datasource.favoritemovies

import androidx.paging.PagingSource
import com.andy.rios.moviesapp.ui.model.database.favoritemovies.FavoriteMovieDao
import com.andy.rios.moviesapp.data.exception.DataNotAvailableException
import com.andy.rios.moviesapp.data.model.favoritemovies.FavoriteMovieDbData
import com.andy.rios.moviesapp.data.model.movies.MovieDbData
import kotlinx.coroutines.withContext
import com.andy.rios.moviesapp.domain.util.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher

class FavoriteMoviesLocalDataSource(
    private val favoriteMovieDao: FavoriteMovieDao,
) : FavoriteMoviesDataSource.Local {

    override fun favoriteMovies(): PagingSource<Int, MovieDbData> = favoriteMovieDao.favoriteMovies()

    override suspend fun addMovieToFavorite(movieId: Int) = withContext(Dispatchers.IO) { favoriteMovieDao.add(FavoriteMovieDbData(movieId)) }


    override suspend fun removeMovieFromFavorite(movieId: Int) = withContext(Dispatchers.IO) {
        favoriteMovieDao.remove(movieId)
    }

    override suspend fun checkFavoriteStatus(movieId: Int): ResultState<Boolean> = withContext(Dispatchers.IO) {
        return@withContext ResultState.Success(favoriteMovieDao.get(movieId) != null)
    }

    override suspend fun getFavoriteMovieIds(): ResultState<List<Int>> = withContext(Dispatchers.IO) {
        val movieIds = favoriteMovieDao.getAll().map { it.movieId }
        return@withContext if (movieIds.isNotEmpty()) {
            ResultState.Success(movieIds)
        } else {
            ResultState.Error(DataNotAvailableException())
        }
    }
}