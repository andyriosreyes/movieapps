package com.andy.rios.moviesapp.ui.model.database.movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andy.rios.moviesapp.data.model.movies.MoviePageDbData

@Dao
interface MoviePageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePage(keys: MoviePageDbData)

    @Query("SELECT * FROM movies_page WHERE id=:id")
    suspend fun getPageByMovieId(id: Int): MoviePageDbData?

    @Query("DELETE FROM movies_page")
    suspend fun clearPage()

    @Query("SELECT * FROM movies_page WHERE id = (SELECT MAX(id) FROM movies_page)")
    suspend fun getLastPage(): MoviePageDbData?
}