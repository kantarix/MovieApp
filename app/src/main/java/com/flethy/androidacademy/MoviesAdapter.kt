package com.flethy.androidacademy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flethy.androidacademy.data.models.Movie
import com.google.android.material.imageview.ShapeableImageView

class MoviesAdapter(private val movieClickListener: OnMovieClickListener) : androidx.recyclerview.widget.ListAdapter<Movie, MoviesAdapter.MovieViewHolder>(MoviesCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater.inflate(R.layout.view_holder_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(getItem(position), movieClickListener)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val logo: ShapeableImageView = itemView.findViewById(R.id.movie_logo)
        private val ageRestriction: AppCompatTextView = itemView.findViewById(R.id.age_restriction)
        private val like: ImageView = itemView.findViewById(R.id.like)
        private val genres: AppCompatTextView = itemView.findViewById(R.id.genres)
        private val reviewsCount: AppCompatTextView = itemView.findViewById(R.id.reviews_count)
        private val title: AppCompatTextView = itemView.findViewById(R.id.movie_title)
        private val duration: AppCompatTextView = itemView.findViewById(R.id.duration)
        private val starList = listOf<ImageView>(
            itemView.findViewById(R.id.star_1),
            itemView.findViewById(R.id.star_2),
            itemView.findViewById(R.id.star_3),
            itemView.findViewById(R.id.star_4),
            itemView.findViewById(R.id.star_5)
        )

        fun onBind(movie: Movie, clickListener: OnMovieClickListener) {
            title.text = movie.title
            Glide.with(itemView.context)
                .load(movie.imageUrl)
                .into(logo)
            ageRestriction.text = itemView.context.getString(R.string.age_restriction, movie.pgAge)
            setLike(movie.isLiked)
            genres.text = movie.genres.map { genre -> genre.name }.joinToString(prefix = "", postfix = "", separator = ", ")
            setRating(movie.rating)
            reviewsCount.text = itemView.context.getString(R.string.reviews_count, movie.reviewCount)
            duration.text = itemView.context.getString(R.string.duration, movie.runningTime)

            itemView.setOnClickListener { movieClickListener.onMovieClicked(movie) }
        }

        private fun setLike(isLike: Boolean) {
            if (isLike) setColor(like, R.color.radical_red)
            else setColor(like, R.color.white_75)
        }

        private fun setRating(rating: Int) {
            for (i in 0 until rating)
                setColor(starList[i], R.color.radical_red)
            for (i in rating until 5)
                setColor(starList[i], R.color.storm_gray)
        }

        private fun setColor(view: ImageView, color: Int) {
            view.setColorFilter(ContextCompat.getColor(itemView.context, color))
        }

    }

}

class MoviesCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem == newItem
}

interface OnMovieClickListener {
    fun onMovieClicked(movie: Movie)
}