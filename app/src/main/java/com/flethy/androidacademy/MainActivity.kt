package com.flethy.androidacademy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
//
//    private val moviesListFragment = FragmentMoviesList.newInstance().apply {
//        setListener(this@MainActivity)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.persistent_container, FragmentMoviesList.newInstance(), "FragmentMoviesList")
                .commit()
        }
    }

//    override fun toMovieDetails(movie: Movie) {
//        supportFragmentManager.beginTransaction()
//            .addToBackStack(null)
//            .add(R.id.persistent_container, FragmentMoviesDetails.newInstance(movie), "FragmentMoviesDetails")
//            .commit()
//    }

//    private val clickListener = object : OnMovieClickListener {
//        override fun onMovieClicked(movie: Movie) {
//            supportFragmentManager.beginTransaction()
//                .addToBackStack(null)
//                .add(R.id.persistent_container, FragmentMoviesDetails.newInstance(movie), "FragmentMoviesDetails")
//                .commit()
//        }
//    }

}