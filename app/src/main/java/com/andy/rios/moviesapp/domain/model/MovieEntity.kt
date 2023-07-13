package com.andy.rios.moviesapp.domain.model

data class MovieEntity(
    val id: Int,
    val title: String?,
    val overview: String?,
    val backdrop_path: String?,
    val vote_average : String?,
    val release_date: String?
) {
}