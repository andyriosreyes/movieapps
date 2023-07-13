package com.andy.rios.moviesapp.data.model.movies

import com.google.gson.annotations.SerializedName

data class MovieData(
    @SerializedName("id") val id: Int,
    @SerializedName("overview") val overview: String?,
    @SerializedName("backdrop_path") val backdrop_path: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("vote_average") val vote_average: String?,
    @SerializedName("release_date") val release_date: String?
) {
}