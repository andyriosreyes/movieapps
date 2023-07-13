package com.andy.rios.moviesapp.di

import android.content.Context
import androidx.room.Room
import com.andy.rios.moviesapp.ui.model.database.favoritemovies.FavoriteMovieDao
import com.andy.rios.moviesapp.ui.model.database.movies.MovieDao
import com.andy.rios.moviesapp.ui.model.database.movies.MovieDatabase
import com.andy.rios.moviesapp.ui.model.database.movies.MoviePageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, "movie.db").build()
    }

    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.movieDao()
    }

    @Provides
    fun provideMovieRemoteKeyDao(movieDatabase: MovieDatabase): MoviePageDao {
        return movieDatabase.moviePageDao()
    }

    @Provides
    fun provideFavoriteMovieDao(movieDatabase: MovieDatabase): FavoriteMovieDao {
        return movieDatabase.movieFavoriteDao()
    }
}