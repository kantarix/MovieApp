package com.flethy.androidacademy.domain

import com.flethy.androidacademy.presentation.movies.viewmodel.MoviesResult

interface MovieRepository {
    suspend fun loadMovies(): MoviesResult
    suspend fun loadMovie(movieId: Int): MoviesResult

    suspend fun updateMovies(): MoviesResult
    suspend fun updateMovie(movieId: Int): MoviesResult
}