package com.andy.rios.moviesapp.di

import com.andy.rios.moviesapp.data.api.MovieApi
import com.andy.rios.moviesapp.ui.model.database.favoritemovies.FavoriteMovieDao
import com.andy.rios.moviesapp.ui.model.database.movies.MovieDao
import com.andy.rios.moviesapp.ui.model.database.movies.MoviePageDao
import com.andy.rios.moviesapp.data.datasource.MovieRemoteMediator
import com.andy.rios.moviesapp.data.datasource.favoritemovies.FavoriteMoviesDataSource
import com.andy.rios.moviesapp.data.datasource.favoritemovies.FavoriteMoviesLocalDataSource
import com.andy.rios.moviesapp.data.datasource.movies.MovieDataSource
import com.andy.rios.moviesapp.data.datasource.movies.MovieLocalDataSource
import com.andy.rios.moviesapp.data.datasource.movies.MovieRemoteDataSource
import com.andy.rios.moviesapp.data.repository.MovieRepositoryImpl
import com.andy.rios.moviesapp.domain.repository.MovieRepository
import com.andy.rios.moviesapp.domain.usecase.AddMovieToFavoriteUseCase
import com.andy.rios.moviesapp.domain.usecase.CheckFavoriteStatusUseCase
import com.andy.rios.moviesapp.domain.usecase.GetFavoriteMoviesUseCase
import com.andy.rios.moviesapp.domain.usecase.GetMovieDetailUseCase
import com.andy.rios.moviesapp.domain.usecase.GetMovieUseCase
import com.andy.rios.moviesapp.domain.usecase.RemoveMovieFromFavoriteUseCase
import com.andy.rios.moviesapp.domain.usecase.SearchMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieRemote: MovieDataSource.Remote,
        movieLocal: MovieDataSource.Local,
        movieRemoteMediator: MovieRemoteMediator,
        favoriteLocal: FavoriteMoviesDataSource.Local,
    ): MovieRepository {
        return MovieRepositoryImpl(movieRemote, movieLocal, movieRemoteMediator, favoriteLocal)
    }

    @Provides
    @Singleton
    fun provideMovieLocalDataSource(
        movieDao: MovieDao,
        movieRemoteKeyDao: MoviePageDao,
    ): MovieDataSource.Local {
        return MovieLocalDataSource(movieDao, movieRemoteKeyDao)
    }

    @Provides
    @Singleton
    fun provideMovieMediator(
        movieLocalDataSource: MovieDataSource.Local,
        movieRemoteDataSource: MovieDataSource.Remote
    ): MovieRemoteMediator {
        return MovieRemoteMediator(movieLocalDataSource, movieRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideFavoriteMovieLocalDataSource(
        favoriteMovieDao: FavoriteMovieDao
    ): FavoriteMoviesDataSource.Local {
        return FavoriteMoviesLocalDataSource(favoriteMovieDao)
    }

    @Provides
    @Singleton
    fun provideMovieRemoveDataSource(movieApi: MovieApi): MovieDataSource.Remote {
        return MovieRemoteDataSource(movieApi)
    }


    @Provides
    fun provideGetMovieDetailsUseCase(movieRepository: MovieRepository): GetMovieDetailUseCase {
        return GetMovieDetailUseCase(movieRepository)
    }

    @Provides
    fun provideGetFavoriteMoviesUseCase(movieRepository: MovieRepository): GetFavoriteMoviesUseCase {
        return GetFavoriteMoviesUseCase(movieRepository)
    }

    @Provides
    fun provideCheckFavoriteStatusUseCase(movieRepository: MovieRepository): CheckFavoriteStatusUseCase {
        return CheckFavoriteStatusUseCase(movieRepository)
    }

    @Provides
    fun provideAddMovieToFavoriteUseCase(movieRepository: MovieRepository): AddMovieToFavoriteUseCase {
        return AddMovieToFavoriteUseCase(movieRepository)
    }

    @Provides
    fun provideRemoveMovieFromFavoriteUseCase(movieRepository: MovieRepository): RemoveMovieFromFavoriteUseCase {
        return RemoveMovieFromFavoriteUseCase(movieRepository)
    }

    @Provides
    fun provideSearchMoviesUseCase(movieRepository: MovieRepository): SearchMovieUseCase {
        return SearchMovieUseCase(movieRepository)
    }
}