package com.flethy.androidacademy.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

class MainActivity : AppCompatActivity(),
    MovieRepositoryProvider,
    FragmentMoviesList.MoviesListItemClickListener,
    FragmentMoviesDetails.MovieDetailsBackClickListener {

    private val networkModule = NetworkModule(NoConnectionInterceptor(this))
    private val remoteDataSource = RetrofitDataSource(networkModule.api, ImageUrlAppender(networkModule.api))
    private val localDataSource = RoomDataSource(db)
    private val movieRepository = MovieRepositoryImpl(localDataSource, remoteDataSource)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            routeToMoviesList()
        }

        val workRepository = WorkRepository()
        WorkManager.getInstance(this).enqueue(workRepository.preloadRequest)
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