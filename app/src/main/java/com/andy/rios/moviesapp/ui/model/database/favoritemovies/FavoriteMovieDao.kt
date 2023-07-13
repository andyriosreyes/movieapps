package com.andy.rios.moviesapp.ui.model.database.favoritemovies

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andy.rios.moviesapp.data.model.favoritemovies.FavoriteMovieDbData
import com.andy.rios.moviesapp.data.model.movies.MovieDbData

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM favorite_movies")
    fun getAll(): List<FavoriteMovieDbData>

    @Query("SELECT * FROM movies where id in (SELECT movieId FROM favorite_movies)")
    fun favoriteMovies(): PagingSource<Int, MovieDbData>

    @Query("SELECT * FROM favorite_movies where movieId=:movieId")
    fun get(movieId: Int): FavoriteMovieDbData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(favoriteMovieDbData: FavoriteMovieDbData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(favoriteMovieDbData: List<FavoriteMovieDbData>)

    @Query("DELETE FROM favorite_movies WHERE movieId=:movieId")
    fun remove(movieId: Int)
}
