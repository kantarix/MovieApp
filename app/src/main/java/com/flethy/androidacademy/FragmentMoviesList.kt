package com.flethy.androidacademy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flethy.androidacademy.data.models.Movie
import com.flethy.androidacademy.domain.MoviesDataSource

class FragmentMoviesList() : Fragment() {

    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvMovies: RecyclerView = view.findViewById(R.id.rv_movies_list)
        adapter = MoviesAdapter(movieClickListener)
        val columnsCount = resources.getInteger(R.integer.rv_movies_columns)
        rvMovies.layoutManager = GridLayoutManager(requireContext(), columnsCount)
        rvMovies.adapter = adapter
        rvMovies.setHasFixedSize(true)
    }

    override fun onStart() {
        super.onStart()
        updateData()
    }

    private fun updateData() {
        adapter.submitList(MoviesDataSource().getMovies())
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
