package com.flethy.androidacademy.data.remote.retrofit

import com.flethy.androidacademy.data.remote.RemoteDataSource
import com.flethy.androidacademy.model.Actor
import com.flethy.androidacademy.model.Genre
import com.flethy.androidacademy.model.Movie
import com.flethy.androidacademy.model.MovieDetails
import kotlin.math.roundToInt

class RetrofitDataSource(
    private val api: MovieApiService,
    private val imageUrlAppender: ImageUrlAppender
): RemoteDataSource {

    override suspend fun loadMovies(): List<Movie> {

        val genres = api.loadGenres().genres

        return api.loadPopularMovies().results.map { movie ->
            Movie(
                id = movie.id,
                title = movie.title,
                imageUrl = imageUrlAppender.getMoviePosterUrl(movie.posterPath),
                rating = (movie.voteAverage/2).roundToInt(),
                reviewCount = movie.voteCount,
                pgAge = movie.adult.getAge(),
                runningTime = 100,
                genres = genres
                    .filter { genreResponse -> movie.genreIds.contains(genreResponse.id) }
                    .map { genre -> Genre(genre.id, genre.name) },
                isLiked = false
            )
        }
    }

    override suspend fun loadMovie(movieId: Int): MovieDetails {
        val details = api.loadMovieDetails(movieId)

        return MovieDetails(
            id = details.id,
            title = details.title,
            storyLine = details.overview,
            detailImageUrl = imageUrlAppender.getDetailPosterUrl(details.backdropPath),
            rating = (details.voteAverage/2).roundToInt(),
            reviewCount = details.voteCount,
            pgAge = details.adult.getAge(),
            genres = details.genres.map { genre -> Genre(genre.id, genre.name) },
            actors = api.loadMovieCast(movieId).cast
                .filter { it.profilePath != null }
                .map { actor ->
                    Actor(
                        actor.id,
                        actor.name,
                        imageUrlAppender.getActorImageUrl(actor.profilePath))
                }
        )
    }

    private fun Boolean.getAge(): Int = if (this) ADULT_AGE else CHILD_AGE

    companion object {
        private const val ADULT_AGE = 16
        private const val CHILD_AGE = 13
    }

}