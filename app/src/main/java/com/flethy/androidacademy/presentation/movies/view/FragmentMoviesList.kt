package com.flethy.androidacademy.presentation.movies.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.flethy.androidacademy.R
import com.flethy.androidacademy.di.MovieRepositoryProvider
import com.flethy.androidacademy.model.Movie
import com.flethy.androidacademy.presentation.movies.viewmodel.MoviesState
import com.flethy.androidacademy.presentation.movies.viewmodel.MoviesViewModel
import com.flethy.androidacademy.presentation.movies.viewmodel.MoviesViewModelFactory

class FragmentMoviesList() : Fragment(R.layout.fragment_movies_list) {

    private val viewModel: MoviesViewModel by viewModels {
        MoviesViewModelFactory((requireActivity() as MovieRepositoryProvider).provideMovieRepository())
    }

    private var listener: MoviesListItemClickListener? = null

    var loader: ProgressBar? = null
    var rvMovies: RecyclerView? = null
    private var swipeRefresh: SwipeRefreshLayout? = null
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MoviesListItemClickListener)
            listener = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setUpListeners()
        setUpMoviesAdapter()

        viewModel.state.observe(this.viewLifecycleOwner, this::updateState)
        viewModel.moviesList.observe(this.viewLifecycleOwner, this::updateData)

        if (savedInstanceState == null) {
            viewModel.loadMovies()
        } else {
            updateData(savedInstanceState.getParcelableArrayList<Movie>(KEY_MOVIES) as List<Movie>)
        }

    }

    override fun onDestroy() {
        destroyViews()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEY_MOVIES, ArrayList<Movie>(moviesAdapter.currentList))
    }

    private fun initViews(view: View) {
        loader = view.findViewById(R.id.loader)
        rvMovies = view.findViewById(R.id.rv_movies_list)
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
    }

    private fun setUpListeners() {
        swipeRefresh?.setOnRefreshListener {
            viewModel.updateMovies()
        }
    }

    private fun setUpMoviesAdapter() {
        moviesAdapter = MoviesAdapter { movie ->
            listener?.onMovieSelected(movieId = movie.id)
        }
        val columnsCount = resources.getInteger(R.integer.rv_movies_columns)
        rvMovies?.apply {
            layoutManager = GridLayoutManager(requireContext(), columnsCount)
            adapter = moviesAdapter
            setHasFixedSize(true)
        }
    }

    private fun destroyViews() {
        loader = null
        rvMovies = null
    }

    private fun updateData(moviesList: List<Movie>) {
        moviesAdapter.submitList(moviesList)
    }

    private fun updateState(state: MoviesState) {
        when (state) {
            is MoviesState.Error -> {
                loader?.isVisible = false
                Toast.makeText(context, "${state.e.message}", Toast.LENGTH_LONG).show()
            }
            is MoviesState.Loading -> {
                if (swipeRefresh?.isRefreshing == false)
                    loader?.isVisible = true
            }
            is MoviesState.Result -> {
                loader?.isVisible = false
                swipeRefresh?.isRefreshing = false
            }
        }
    }

    interface MoviesListItemClickListener {
        fun onMovieSelected(movieId: Int)
    }

    companion object {
        private const val KEY_MOVIES = "KEY_MOVIES"
        fun newInstance() = FragmentMoviesList()
    }

}
