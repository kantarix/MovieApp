package com.flethy.androidacademy.presentation.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flethy.androidacademy.data.models.Movie
import com.flethy.androidacademy.domain.MovieRepository
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val movieRepo: MovieRepository
): ViewModel() {

    private val _moviesList = MutableLiveData<List<Movie>>(emptyList())
    val moviesList: LiveData<List<Movie>> get() = _moviesList

    fun updateMovies() {
        viewModelScope.launch {
            val newMovies = movieRepo.loadMovies()
            _moviesList.postValue(newMovies)
        }
    }

}