package com.flethy.androidacademy.domain

import com.flethy.androidacademy.data.models.Movie

interface MovieRepository {
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovie(movieId: Int): Movie
}