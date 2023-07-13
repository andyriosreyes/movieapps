package com.andy.rios.moviesapp.data.api

import com.andy.rios.moviesapp.data.model.MovieMainData
import com.andy.rios.moviesapp.data.model.movies.MovieData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular?language=ES")
    suspend fun getMovies(
        @Query("page") page: Int,
    ): MovieMainData

    @GET("/movies/{id}")
    suspend fun getMovie(@Path("id") movieId: Int): MovieData

    @GET("search/movie?include_adult=false&language=ES")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieMainData
}