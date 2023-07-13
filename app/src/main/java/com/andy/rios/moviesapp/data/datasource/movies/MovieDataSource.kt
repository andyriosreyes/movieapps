package com.andy.rios.moviesapp.data.datasource.movies

import androidx.paging.PagingSource
import com.andy.rios.moviesapp.data.model.movies.MovieDbData
import com.andy.rios.moviesapp.data.model.movies.MoviePageDbData
import com.andy.rios.moviesapp.domain.model.MovieEntity
import com.andy.rios.moviesapp.domain.util.ResultState

interface MovieDataSource {

    interface Remote {
        suspend fun getMovies(page: Int, limit: Int): ResultState<List<MovieEntity>>
        suspend fun getMovie(movieId: Int): ResultState<MovieEntity>
        suspend fun search(query: String, page: Int, limit: Int): ResultState<List<MovieEntity>>
    }

    interface Local {
        fun movies(): PagingSource<Int, MovieDbData>
        suspend fun getMovies(): ResultState<List<MovieEntity>>
        suspend fun getMovie(movieId: Int): ResultState<MovieEntity>
        suspend fun saveMovies(movieEntities: List<MovieEntity>)
        suspend fun getLastPage(): MoviePageDbData?
        suspend fun savePage(key: MoviePageDbData)
        suspend fun clearMovies()
        suspend fun clearPage()
    }
}