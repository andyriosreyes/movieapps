package com.andy.rios.moviesapp.ui.model.database.movies

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andy.rios.moviesapp.ui.model.database.favoritemovies.FavoriteMovieDao
import com.andy.rios.moviesapp.data.model.movies.MovieDbData
import com.andy.rios.moviesapp.data.model.favoritemovies.FavoriteMovieDbData
import com.andy.rios.moviesapp.data.model.movies.MoviePageDbData

@Database(
    entities = [MovieDbData::class, FavoriteMovieDbData::class, MoviePageDbData::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun moviePageDao(): MoviePageDao
    abstract fun movieFavoriteDao(): FavoriteMovieDao
}