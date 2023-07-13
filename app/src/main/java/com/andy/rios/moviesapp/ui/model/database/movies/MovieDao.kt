package com.andy.rios.moviesapp.ui.model.database.movies

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andy.rios.moviesapp.data.model.movies.MovieDbData

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY id")
    fun movies(): PagingSource<Int, MovieDbData>

    @Query("SELECT * FROM movies ORDER BY id")
    fun getMovies(): List<MovieDbData>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovie(movieId: Int): MovieDbData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<MovieDbData>)

    @Query("DELETE FROM movies WHERE id NOT IN (SELECT movieId FROM favorite_movies)")
    suspend fun clearMoviesExceptFavorites()
}