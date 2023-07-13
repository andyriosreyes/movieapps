package com.andy.rios.moviesapp.ui.mapper

import com.andy.rios.moviesapp.data.util.Constants.URL_IMAGE
import com.andy.rios.moviesapp.domain.model.MovieEntity
import com.andy.rios.moviesapp.ui.model.MovieList


fun MovieEntity.toPresentationList() = MovieList(
    id = id,
    title = title,
    overview = overview,
    imageUrl = URL_IMAGE + backdrop_path,
    vote_average = vote_average,
    release_date = release_date
)