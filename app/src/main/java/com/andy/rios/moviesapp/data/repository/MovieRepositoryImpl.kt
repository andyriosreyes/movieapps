package com.andy.rios.moviesapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.andy.rios.moviesapp.data.datasource.MovieRemoteMediator
import com.andy.rios.moviesapp.data.datasource.SearchMoviePagingSource
import com.andy.rios.moviesapp.data.datasource.favoritemovies.FavoriteMoviesDataSource
import com.andy.rios.moviesapp.data.datasource.movies.MovieDataSource
import com.andy.rios.moviesapp.data.mapper.toDomain
import com.andy.rios.moviesapp.domain.model.MovieEntity
import com.andy.rios.moviesapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Collections
import com.andy.rios.moviesapp.domain.util.ResultState
import com.andy.rios.moviesapp.domain.util.getResult
import com.andy.rios.moviesapp.domain.util.onSuccess
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remote: MovieDataSource.Remote,
    private val local: MovieDataSource.Local,
    private val remoteMediator: MovieRemoteMediator,
    private val localFavorite: FavoriteMoviesDataSource.Local
) : MovieRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun movies(pageSize: Int): Flow<PagingData<MovieEntity>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        remoteMediator = remoteMediator,
        pagingSourceFactory = { local.movies() }
    ).flow.map { pagingData ->
        pagingData.map { it.toDomain() }
    }


    override fun favoriteMovies(pageSize: Int): Flow<PagingData<MovieEntity>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { localFavorite.favoriteMovies() }
    ).flow.map { pagingData ->
        pagingData.map { it.toDomain() }
    }

    override fun search(query: String, pageSize: Int): Flow<PagingData<MovieEntity>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { SearchMoviePagingSource(query, remote) }
    ).flow

    override suspend fun getMovie(movieId: Int): ResultState<MovieEntity> = local.getMovie(movieId).getResult({
        it
    }, {
        remote.getMovie(movieId)
    })

    override suspend fun checkFavoriteStatus(movieId: Int): ResultState<Boolean> = localFavorite.checkFavoriteStatus(movieId)
//
    override suspend fun addMovieToFavorite(movieId: Int) {
        getMovie(movieId).onSuccess {
            local.saveMovies(Collections.singletonList(it))
            localFavorite.addMovieToFavorite(movieId)
        }
    }
//
    override suspend fun removeMovieFromFavorite(movieId: Int) = localFavorite.removeMovieFromFavorite(movieId)
//
//    override suspend fun sync(): Boolean = local.getMovies().getResult({ movieIdsResult ->
//        remote.getMovies(movieIdsResult.data.map { it.id }).getResult({
//            local.saveMovies(it.data)
//            true
//        }, {
//            it.error is DataNotAvailableException
//        })
//    }, {
//        it.error is DataNotAvailableException
//    })
}
