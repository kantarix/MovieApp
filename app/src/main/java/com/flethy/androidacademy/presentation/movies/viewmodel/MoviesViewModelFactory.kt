package com.flethy.androidacademy.presentation.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flethy.androidacademy.domain.MovieRepository

@Suppress("UNCHECKED_CAST")
class MoviesViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = MoviesViewModel(repository) as T
}