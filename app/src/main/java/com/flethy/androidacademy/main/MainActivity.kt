package com.flethy.androidacademy.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.work.WorkManager
import com.flethy.androidacademy.MovieApp.Companion.db
import com.flethy.androidacademy.R
import com.flethy.androidacademy.background.WorkRepository
import com.flethy.androidacademy.data.MovieRepositoryImpl
import com.flethy.androidacademy.data.local.room.RoomDataSource
import com.flethy.androidacademy.data.remote.retrofit.ImageUrlAppender
import com.flethy.androidacademy.data.remote.retrofit.RetrofitDataSource
import com.flethy.androidacademy.di.MovieRepositoryProvider
import com.flethy.androidacademy.di.NetworkModule
import com.flethy.androidacademy.di.NoConnectionInterceptor
import com.flethy.androidacademy.domain.MovieRepository
import com.flethy.androidacademy.presentation.movieDetails.view.FragmentMoviesDetails
import com.flethy.androidacademy.presentation.movies.view.FragmentMoviesList
import java.lang.Boolean.getBoolean

class MainActivity : AppCompatActivity(),
    MovieRepositoryProvider,
    FragmentMoviesList.MoviesListItemClickListener,
    FragmentMoviesDetails.MovieDetailsBackClickListener {

    private val networkModule = NetworkModule(NoConnectionInterceptor(this))
    private val remoteDataSource = RetrofitDataSource(networkModule.api, ImageUrlAppender(networkModule.api))
    private val localDataSource = RoomDataSource(db)
    private val movieRepository = MovieRepositoryImpl(localDataSource, remoteDataSource)

    companion object {
        private const val FRAGMENT_MOVIE_DETAILS = "movie_details"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            routeToMoviesList()
        }

        val isNotificationIntent = intent?.extras?.getBoolean("MovieNotificationIntent") ?: false
        if (isNotificationIntent && intent != null) {
            handleIntent(intent)
        }

        val workRepository = WorkRepository()
        WorkManager.getInstance(this).enqueue(workRepository.preloadRequest)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null)
            handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                val movieId = intent.data?.lastPathSegment?.toIntOrNull()
                if (movieId != null) {
                    routeToMovieDetails(movieId)
                }
            }
        }
    }

    override fun onMovieSelected(movieId: Int) {
        routeToMovieDetails(movieId)
    }

    override fun onMovieDeselected() {
        routeBack()
    }

    private fun routeToMoviesList() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.persistent_container, FragmentMoviesList.newInstance(), "FragmentMoviesList")
            .commit()
    }

    private fun routeToMovieDetails(movieId: Int) {
        supportFragmentManager.beginTransaction()
            .add(R.id.persistent_container, FragmentMoviesDetails.newInstance(movieId), "FragmentMovieDetails")
            .addToBackStack(null)
            .commit()
    }

    private fun routeBack() {
        supportFragmentManager.popBackStack()
    }

    override fun provideMovieRepository(): MovieRepository = movieRepository

}