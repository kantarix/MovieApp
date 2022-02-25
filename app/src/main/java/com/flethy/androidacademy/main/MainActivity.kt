package com.flethy.androidacademy.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flethy.androidacademy.R
import com.flethy.androidacademy.data.MovieRepositoryImpl
import com.flethy.androidacademy.data.models.Movie
import com.flethy.androidacademy.di.MovieRepositoryProvider
import com.flethy.androidacademy.domain.MovieRepository
import com.flethy.androidacademy.presentation.movieDetails.view.FragmentMoviesDetails
import com.flethy.androidacademy.presentation.movies.view.FragmentMoviesList

class MainActivity : AppCompatActivity(),
    MovieRepositoryProvider,
    FragmentMoviesList.MoviesListItemClickListener,
    FragmentMoviesDetails.MovieDetailsBackClickListener {

    private val movieRepository = MovieRepositoryImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            routeToMoviesList()
        }
    }

    override fun onMovieSelected(movie: Movie) {
        routeToMovieDetails(movie)
    }

    override fun onMovieDeselected() {
        routeBack()
    }

    private fun routeToMoviesList() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.persistent_container, FragmentMoviesList.newInstance(), "FragmentMoviesList")
            .commit()
    }

    private fun routeToMovieDetails(movie: Movie) {
        supportFragmentManager.beginTransaction()
            .add(R.id.persistent_container, FragmentMoviesDetails.newInstance(movie), "FragmentMovieDetails")
            .addToBackStack(null)
            .commit()
    }

    private fun routeBack() {
        supportFragmentManager.popBackStack()
    }

    override fun provideMovieRepository(): MovieRepository = movieRepository

}