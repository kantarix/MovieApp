package com.flethy.androidacademy.data.remote

import com.flethy.androidacademy.model.Movie
import com.flethy.androidacademy.model.MovieDetails

interface RemoteDataSource {

    suspend fun loadMovies(): List<Movie>

    suspend fun loadMovie(movieId: Int): MovieDetails

}