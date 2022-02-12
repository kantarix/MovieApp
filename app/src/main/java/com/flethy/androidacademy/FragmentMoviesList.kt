package com.flethy.androidacademy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flethy.androidacademy.data.JsonMovieRepository
import com.flethy.androidacademy.data.models.Movie
import kotlinx.coroutines.*

class FragmentMoviesList() : Fragment() {

    private var coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    private lateinit var moviesAdapter: MoviesAdapter

    var rvMovies: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findViews(view)

    }

    private fun findViews(view: View) {
        rvMovies = view.findViewById(R.id.rv_movies_list)
        moviesAdapter = MoviesAdapter(movieClickListener)
        val columnsCount = resources.getInteger(R.integer.rv_movies_columns)
        rvMovies?.apply {
            layoutManager = GridLayoutManager(requireContext(), columnsCount)
            adapter = moviesAdapter
            setHasFixedSize(true)
        }
    }

    override fun onStart() {
        super.onStart()
        updateData()
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }

    private fun updateData() {
        coroutineScope.launch {
            val moviesList = JsonMovieRepository(requireContext()).loadMovies()
            moviesAdapter.submitList(moviesList)
        }
    }

    companion object {
        fun newInstance() = FragmentMoviesList()
    }

    private var movieClickListener = object : OnMovieClickListener {
        override fun onMovieClicked(movie: Movie) {
            fragmentManager?.let {
                it.beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.persistent_container, FragmentMoviesDetails.newInstance(movie), "FragmentMoviesDetails")
                    .commit()
            }
        }
    }

}
