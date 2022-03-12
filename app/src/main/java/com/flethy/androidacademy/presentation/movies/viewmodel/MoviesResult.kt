package com.flethy.androidacademy.presentation.movies.viewmodel

import com.flethy.androidacademy.model.Movie
import com.flethy.androidacademy.model.MovieDetails

sealed class MoviesResult {
    class ValidResultMoviesList(val moviesList: List<Movie>) : MoviesResult()
    class ValidResultMovie(val movie: MovieDetails) : MoviesResult()
    class ErrorResult(val e: Throwable) : MoviesResult()
}

