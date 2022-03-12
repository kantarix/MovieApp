package com.flethy.androidacademy.presentation.movies.viewmodel

sealed class MoviesState {
    class Result : MoviesState()
    class Loading : MoviesState()
    class Error(val e: Throwable) : MoviesState()
}

