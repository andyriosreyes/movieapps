package com.andy.rios.moviesapp.data.model.movies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_page")
data class MoviePageDbData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val prevPage: Int?,
    val nextPage: Int?
)