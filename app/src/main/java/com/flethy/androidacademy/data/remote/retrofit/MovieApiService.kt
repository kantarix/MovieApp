package com.flethy.androidacademy.data.remote.retrofit

import com.flethy.androidacademy.data.remote.retrofit.response.*
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiService {

    @GET("configuration")
    suspend fun loadConfiguration(): ConfigurationResponse

    @GET("movie/popular")
    suspend fun loadPopularMovies(): PopularResponse

    @GET("movie/{movie_id}")
    suspend fun loadMovieDetails(@Path("movie_id") movieId: Int): MovieDetailsResponse

    @GET("movie/{movie_id}/credits")
    suspend fun loadMovieCast(@Path("movie_id") movieId: Int): MovieCastResponse

    @GET("genre/movie/list")
    suspend fun loadGenres(): GenresResponse

}