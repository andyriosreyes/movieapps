package com.andy.rios.moviesapp.data.mapper

import com.andy.rios.moviesapp.data.model.movies.MovieData
import com.andy.rios.moviesapp.data.model.movies.MovieDbData
import com.andy.rios.moviesapp.data.util.Constants.URL_IMAGE
import com.andy.rios.moviesapp.domain.model.MovieEntity

fun MovieData.toDomain() = MovieEntity(
    id = id,
    backdrop_path = URL_IMAGE + backdrop_path,
    overview = overview,
    title = title,
    vote_average = vote_average,
    release_date = release_date
)

fun MovieEntity.toDbData() = MovieDbData(
    id = id,
    backdrop_path = URL_IMAGE + backdrop_path,
    overview = overview,
    title = title,
    vote_average = vote_average,
    release_date = release_date
)

fun MovieDbData.toDomain() = MovieEntity(
    id = id,
    backdrop_path = backdrop_path,
    overview = overview,
    title = title,
    vote_average = vote_average,
    release_date = release_date
)
