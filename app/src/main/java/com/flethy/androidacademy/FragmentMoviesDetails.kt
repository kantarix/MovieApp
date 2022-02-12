package com.flethy.androidacademy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flethy.androidacademy.data.models.Movie

class FragmentMoviesDetails : Fragment() {

    private lateinit var actorsAdapter: ActorsAdapter
    private lateinit var movie: Movie

    private var movieTitle: AppCompatTextView? = null
    private var movieLogo: ImageView? = null
    private var ageRestriction: AppCompatTextView? = null
    private var genres: AppCompatTextView? = null
    private var reviewsCount: AppCompatTextView? = null
    private var storyline: AppCompatTextView? = null
    private var starList: List<ImageView> = listOf()
    private var rvActors: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { movie = it.getSerializable(PARAM_MOVIE) as Movie }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findViews(view)

        view.findViewById<AppCompatTextView>(R.id.btn_back).setOnClickListener {
            fragmentManager?.popBackStack()
        }

    }

    private fun findViews(view: View) {
        movieTitle = view.findViewById(R.id.movie_title)
        movieLogo = view.findViewById(R.id.movie_logo)
        ageRestriction = view.findViewById(R.id.age_restriction)
        genres = view.findViewById(R.id.genres)
        reviewsCount = view.findViewById(R.id.reviews_count)
        storyline = view.findViewById(R.id.movie_storyline)
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

    override fun onStart() {
        super.onStart()
        updateData()
    }

    override fun onDestroy() {
        destroyViews()
        super.onDestroy()
    }

    private fun destroyViews() {
        movieTitle = null
        movieLogo = null
        ageRestriction = null
        genres = null
        reviewsCount = null
        storyline = null
        starList = listOf()
        rvActors = null
    }

    private fun updateData() {
        actorsAdapter.submitList(movie.actors)

        movieTitle?.text = movie.title
        movieLogo?.let {
            Glide.with(this)
                .load(movie.detailImageUrl)
                .into(it)
        }
        ageRestriction?.text = getString(R.string.age_restriction, movie.pgAge)
        genres?.text = movie.genres.map { genre -> genre.name }.joinToString(prefix = "", postfix = "", separator = ", ")
        reviewsCount?.text = getString(R.string.reviews_count, movie.reviewCount)
        storyline?.text = movie.storyLine
        setRating(movie.rating)
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

    companion object {

        private const val PARAM_MOVIE = "movie"

        fun newInstance(movie: Movie): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            val args = Bundle()
            args.putSerializable(PARAM_MOVIE, movie)
            fragment.arguments = args
            return fragment
        }
    }
}