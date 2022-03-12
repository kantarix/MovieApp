package com.flethy.androidacademy.data

import com.flethy.androidacademy.data.remote.RemoteDataSource
import com.flethy.androidacademy.domain.MovieRepository
import com.flethy.androidacademy.presentation.movies.viewmodel.MoviesResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class MovieRepositoryImpl(private val remoteDataSource: RemoteDataSource) : MovieRepository {

    override suspend fun loadMovies(): MoviesResult {
        return withContext(Dispatchers.IO) {
            try {
                val movies = remoteDataSource.loadMovies()
                MoviesResult.ValidResultMoviesList(movies)
            } catch (e: Throwable) {
                MoviesResult.ErrorResult(e)
            }
        }
    }

    override suspend fun loadMovie(movieId: Int): MoviesResult {
        return withContext(Dispatchers.IO) {
            try {
                val movie = remoteDataSource.loadMovie(movieId)
                MoviesResult.ValidResultMovie(movie)
            } catch (e: Throwable) {
                MoviesResult.ErrorResult(e)
            }

        }
    }

}
