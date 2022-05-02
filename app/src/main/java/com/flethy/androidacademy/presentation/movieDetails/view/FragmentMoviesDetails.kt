package com.flethy.androidacademy.presentation.movieDetails.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.flethy.androidacademy.R
import com.flethy.androidacademy.di.MovieRepositoryProvider
import com.flethy.androidacademy.model.MovieDetails
import com.flethy.androidacademy.presentation.movies.viewmodel.MoviesState
import com.flethy.androidacademy.presentation.movies.viewmodel.MoviesViewModel
import com.flethy.androidacademy.presentation.movies.viewmodel.MoviesViewModelFactory
import kotlin.properties.Delegates

class FragmentMoviesDetails : Fragment(R.layout.fragment_movies_details) {

    private val viewModel: MoviesViewModel by viewModels {
        MoviesViewModelFactory((requireActivity() as MovieRepositoryProvider).provideMovieRepository())
    }

    private var listener: MovieDetailsBackClickListener? = null

    private lateinit var actorsAdapter: ActorsAdapter
    private var movieId by Delegates.notNull<Int>()

    private var movieTitle: AppCompatTextView? = null
    private var movieLogo: ImageView? = null
    private var ageRestriction: AppCompatTextView? = null
    private var genres: AppCompatTextView? = null
    private var reviewsCount: AppCompatTextView? = null
    private var storyline: AppCompatTextView? = null
    private var starList: List<ImageView> = listOf()
    private var rvActors: RecyclerView? = null
    private var btnBack: AppCompatTextView? = null
    private var loader: ProgressBar? = null
    private var castBlock: AppCompatTextView? = null
    private var storylineBlock: AppCompatTextView? = null
    private var swipeRefresh: SwipeRefreshLayout? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MovieDetailsBackClickListener)
            listener = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { movieId = it.getInt(PARAM_MOVIE) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setUpListeners()

        viewModel.state.observe(this.viewLifecycleOwner, this::updateState)
        viewModel.currentMovie.observe(this.viewLifecycleOwner, this::updateData)
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadMovie(movieId)
    }

    override fun onDestroy() {
        destroyViews()
        super.onDestroy()
    }

    private fun initViews(view: View) {
        loader = view.findViewById(R.id.loader)
        castBlock = view.findViewById(R.id.cast_block_title)
        storylineBlock = view.findViewById(R.id.storyline_block_title)
        btnBack = view.findViewById(R.id.btn_back)
        movieTitle = view.findViewById(R.id.movie_title)
        movieLogo = view.findViewById(R.id.movie_logo)
        ageRestriction = view.findViewById(R.id.age_restriction)
        genres = view.findViewById(R.id.genres)
        reviewsCount = view.findViewById(R.id.reviews_count)
        storyline = view.findViewById(R.id.movie_storyline)
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        starList = listOf(
            view.findViewById(R.id.star_1),
            view.findViewById(R.id.star_2),
            view.findViewById(R.id.star_3),
            view.findViewById(R.id.star_4),
            view.findViewById(R.id.star_5)
        )
        rvActors = view.findViewById(R.id.rv_actors)
        actorsAdapter = ActorsAdapter()
        rvActors?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = actorsAdapter
            setHasFixedSize(true)
        }
    }

    private fun setUpListeners() {
        btnBack?.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        swipeRefresh?.setOnRefreshListener {
            viewModel.updateMovie(movieId)
        }
    }

    private fun updateData(movie: MovieDetails) {
        actorsAdapter.submitList(movie.actors)

        movieTitle?.text = movie.title
        movieLogo?.let {
            Glide.with(this)
                .load(movie.detailImageUrl)
                .into(it)
        }
        ageRestriction?.text = getString(R.string.age_restriction, movie.pgAge)
        genres?.text = movie.genres?.map { genre -> genre.name }?.joinToString(prefix = "", postfix = "", separator = ", ")
        reviewsCount?.text = getString(R.string.reviews_count, movie.reviewCount)
        storyline?.text = movie.storyLine
        setRating(movie.rating)
    }

    private fun updateState(state: MoviesState) {
        when (state) {
            is MoviesState.Error -> {
                loader?.isVisible = false
                Toast.makeText(context, "${state.e.message}", Toast.LENGTH_LONG).show()
            }
            is MoviesState.Loading -> {
                if (swipeRefresh?.isRefreshing == false) {
                    loader?.isVisible = true
                    starList.forEach { it.isVisible = false }
                    castBlock?.isVisible = false
                    storylineBlock?.isVisible = false
                }
            }
            is MoviesState.Result -> {
                swipeRefresh?.isRefreshing = false
                loader?.isVisible = false
                starList.forEach { it.isVisible = true }
                castBlock?.isVisible = true
                storylineBlock?.isVisible = true
            }
        }
    }

    private fun setRating(rating: Int) {
        for (i in 0 until rating)
            setColor(starList[i], R.color.radical_red)
        for (i in rating until 5)
            setColor(starList[i], R.color.storm_gray)
    }

    private fun setColor(view: ImageView, color: Int) {
        view.setColorFilter(ContextCompat.getColor(requireContext(), color))
    }

    private fun destroyViews() {
        storylineBlock = null
        castBlock = null
        loader = null
        movieTitle = null
        movieLogo = null
        ageRestriction = null
        genres = null
        reviewsCount = null
        storyline = null
        starList = listOf()
        rvActors = null
        btnBack = null
    }

    interface MovieDetailsBackClickListener {
        fun onMovieDeselected()
    }

    companion object {
        private const val PARAM_MOVIE = "MOVIE_ID"

        fun newInstance(movieId: Int): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            val args = Bundle()
            args.putInt(PARAM_MOVIE, movieId)
            fragment.arguments = args
            return fragment
        }
    }
}