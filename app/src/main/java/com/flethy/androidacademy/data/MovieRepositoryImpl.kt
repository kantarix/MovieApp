package com.flethy.androidacademy.data

import android.util.Log
import com.flethy.androidacademy.data.local.LocalDataSource
import com.flethy.androidacademy.data.remote.RemoteDataSource
import com.flethy.androidacademy.domain.MovieRepository
import com.flethy.androidacademy.model.Movie
import com.flethy.androidacademy.model.MovieDetails
import com.flethy.androidacademy.presentation.movies.viewmodel.MoviesResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class MovieRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
    ) : MovieRepository {

    override suspend fun loadMovies(): MoviesResult {
        return withContext(Dispatchers.IO) {
            var movies: List<Movie>
            try {
                movies = localDataSource.loadMovies()
                if (movies.isEmpty()) {
                    movies = remoteDataSource.loadMovies()
                    localDataSource.insertMovies(movies)
                }
                MoviesResult.ValidResultMoviesList(movies)
            } catch (e: Throwable) {
                MoviesResult.ErrorResult(e)
            }
        }
    }

    override suspend fun loadMovie(movieId: Int): MoviesResult {
        return withContext(Dispatchers.IO) {
            try {
                if (localDataSource.exists(movieId)) {
                    val movie = localDataSource.loadMovie(movieId)
                    MoviesResult.ValidResultMovie(movie)
                } else {
                    val movie = remoteDataSource.loadMovie(movieId)
                    localDataSource.insertMovie(movie)
                    MoviesResult.ValidResultMovie(movie)
                }
            } catch (e: Throwable) {
                MoviesResult.ErrorResult(e)
            }
        }
    }

    override suspend fun updateMovies(): MoviesResult {
        return withContext(Dispatchers.IO) {
            try {
                val movies = remoteDataSource.loadMovies()
                localDataSource.insertMovies(movies)
                MoviesResult.ValidResultMoviesList(movies)
            } catch (e: Throwable) {
                MoviesResult.ErrorResult(e)
            }
        }
    }

    override suspend fun updateMovie(movieId: Int): MoviesResult {
        return withContext(Dispatchers.IO) {
            try {
                val movie = remoteDataSource.loadMovie(movieId)
                localDataSource.insertMovie(movie)
                MoviesResult.ValidResultMovie(movie)
            } catch (e: Throwable) {
                MoviesResult.ErrorResult(e)
            }
        }
    }

}
