package com.flethy.androidacademy.data.local

import com.flethy.androidacademy.model.Movie
import com.flethy.androidacademy.model.MovieDetails

interface LocalDataSource {

    suspend fun loadMovies(): List<Movie>

    suspend fun insertMovies(moviesFromNetwork: List<Movie>)

    suspend fun loadMovie(movieId: Int): MovieDetails

    suspend fun insertMovie(movieFromNetwork: MovieDetails)

    suspend fun exists(movieId: Int): Boolean

}