package com.andy.rios.moviesapp.data.model.movies

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class MovieDbData(
    @PrimaryKey val id: Int,
    val overview: String?,
    val backdrop_path: String?,
    val title: String?,
    val vote_average : String?,
    val release_date: String?
)