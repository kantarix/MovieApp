package com.flethy.androidacademy.background

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.flethy.androidacademy.MovieApp
import com.flethy.androidacademy.data.MovieNotification
import com.flethy.androidacademy.data.MovieRepositoryImpl
import com.flethy.androidacademy.data.Notifications
import com.flethy.androidacademy.data.local.room.RoomDataSource
import com.flethy.androidacademy.data.remote.retrofit.ImageUrlAppender
import com.flethy.androidacademy.data.remote.retrofit.RetrofitDataSource
import com.flethy.androidacademy.di.NetworkModule
import com.flethy.androidacademy.di.NoConnectionInterceptor
import com.flethy.androidacademy.model.Movie
import com.flethy.androidacademy.presentation.movies.viewmodel.MoviesResult
import com.flethy.androidacademy.presentation.movies.viewmodel.MoviesState
import kotlinx.coroutines.*
import okhttp3.internal.notify

class PreloadWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    private val networkModule = NetworkModule(NoConnectionInterceptor(context))
    private val remoteDataSource = RetrofitDataSource(networkModule.api, ImageUrlAppender(networkModule.api))
    private val localDataSource = RoomDataSource(MovieApp.db)
    private val movieRepository = MovieRepositoryImpl(localDataSource, remoteDataSource)

    private val movieNotification = MovieNotification(context)

    private val handlerException = CoroutineExceptionHandler {
            _, throwable ->
                Log.d("Coroutine exception", "Exception handled: ${throwable.message}")
    }

    override fun doWork(): Result {

        movieNotification.initialize()

        return try {
            CoroutineScope(Dispatchers.IO + Job() + handlerException).launch {
                val result = movieRepository.updateMovies()
                if (result is MoviesResult.ValidResultMoviesList) {
                    result.moviesList.forEach { movie -> movieRepository.updateMovie(movieId = movie.id) }

                    val highestRatingMovie = getHighestRatingMovie(result.moviesList)
                    if (highestRatingMovie != null)
                        movieNotification.showNotification(highestRatingMovie)
                }
            }
            Result.success()
        } catch (e: Throwable) {
            Result.failure()
        }
    }

    private fun getHighestRatingMovie(movieList: List<Movie>): Movie? =
        movieList.maxByOrNull { it.rating }
}