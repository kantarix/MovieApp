package com.flethy.androidacademy.presentation.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flethy.androidacademy.domain.MovieRepository
import com.flethy.androidacademy.model.Movie
import com.flethy.androidacademy.model.MovieDetails
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val movieRepo: MovieRepository
): ViewModel() {

    private val _moviesList = MutableLiveData<List<Movie>>(emptyList())
    val moviesList: LiveData<List<Movie>> get() = _moviesList

    private val _currentMovie = MutableLiveData<MovieDetails>()
    val currentMovie: LiveData<MovieDetails> get() = _currentMovie

    private val _state = MutableLiveData<MoviesState>(MoviesState.Result())
    val state: LiveData<MoviesState> get() = _state

    fun loadMovies() {
        viewModelScope.launch {

            _state.postValue(MoviesState.Loading())

            when (val result = movieRepo.loadMovies()) {
                is MoviesResult.ValidResultMoviesList -> {
                    _moviesList.postValue(result.moviesList)
                    _state.postValue(MoviesState.Result())
                }
                is MoviesResult.ErrorResult -> {
                    _state.postValue(MoviesState.Error(result.e))
                    _moviesList.postValue(emptyList())
                }
                else -> {}
            }

        }
    }

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {

            _state.postValue(MoviesState.Loading())

            when (val result = movieRepo.loadMovie(movieId)) {
                is MoviesResult.ValidResultMovie -> {
                    _currentMovie.postValue(result.movie)
                    _state.postValue(MoviesState.Result())
                }
                is MoviesResult.ErrorResult -> {
                    _state.postValue(MoviesState.Error(result.e))
                }
                else -> {}
            }

        }
    }

    fun updateMovies() {

        _state.postValue(MoviesState.Loading())

        viewModelScope.launch {
            when (val result = movieRepo.updateMovies()) {
                is MoviesResult.ValidResultMoviesList -> {
                    _moviesList.postValue(result.moviesList)
                    _state.postValue(MoviesState.Result())
                }
                else -> {
                    _state.postValue(MoviesState.Result())
                }
            }
        }
    }

    fun updateMovie(movieId: Int) {

        _state.postValue(MoviesState.Loading())

        viewModelScope.launch {
            when (val result = movieRepo.updateMovie(movieId)) {
                is MoviesResult.ValidResultMovie -> {
                    _currentMovie.postValue(result.movie)
                    _state.postValue(MoviesState.Result())
                }
                else -> {
                    _state.postValue(MoviesState.Result())
                }
            }
        }
    }

}