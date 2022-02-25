package com.flethy.androidacademy.di

import com.flethy.androidacademy.domain.MovieRepository

internal interface MovieRepositoryProvider {
    fun provideMovieRepository(): MovieRepository
}