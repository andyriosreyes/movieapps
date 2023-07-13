package com.andy.rios.moviesapp.data.model

import com.andy.rios.moviesapp.data.model.movies.MovieData
import com.google.gson.annotations.SerializedName

data class MovieMainData(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: ArrayList<MovieData>
) {
}