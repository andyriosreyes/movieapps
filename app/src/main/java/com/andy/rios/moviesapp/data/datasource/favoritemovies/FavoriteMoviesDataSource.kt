package com.andy.rios.moviesapp.data.datasource.favoritemovies

import androidx.paging.PagingSource
import com.andy.rios.moviesapp.data.model.movies.MovieDbData
import com.andy.rios.moviesapp.domain.util.ResultState

interface FavoriteMoviesDataSource {

    interface Local {
        fun favoriteMovies(): PagingSource<Int, MovieDbData>
        suspend fun getFavoriteMovieIds(): ResultState<List<Int>>
        suspend fun addMovieToFavorite(movieId: Int)
        suspend fun removeMovieFromFavorite(movieId: Int)
        suspend fun checkFavoriteStatus(movieId: Int): ResultState<Boolean>
    }
}
