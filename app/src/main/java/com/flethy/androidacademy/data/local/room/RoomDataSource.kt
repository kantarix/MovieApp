package com.flethy.androidacademy.data.local.room

import com.flethy.androidacademy.data.local.LocalDataSource
import com.flethy.androidacademy.data.local.room.entity.*
import com.flethy.androidacademy.model.Actor
import com.flethy.androidacademy.model.Genre
import com.flethy.androidacademy.model.Movie
import com.flethy.androidacademy.model.MovieDetails

class RoomDataSource(private val db: AppRoomDatabase) : LocalDataSource {
    override suspend fun loadMovies(): List<Movie> {
        return db.getMovieDao().getMovies().map { movieWithGenres ->
            Movie(
                id = movieWithGenres.movie.movieId,
                title = movieWithGenres.movie.title,
                imageUrl = movieWithGenres.movie.imageUrl,
                rating = movieWithGenres.movie.rating,
                reviewCount = movieWithGenres.movie.reviewCount,
                pgAge = movieWithGenres.movie.pgAge,
                runningTime = movieWithGenres.movie.runningTime,
                genres = movieWithGenres.genres.map { genreDb -> Genre(id = genreDb.genreId, name = genreDb.name) },
                isLiked = movieWithGenres.movie.isLiked
            )
        }
    }

    override suspend fun insertMovies(moviesFromNetwork: List<Movie>) {
        val movies = moviesFromNetwork.map { movie ->
            MovieDb(
                movieId = movie.id,
                pgAge = movie.pgAge,
                title = movie.title,
                imageUrl = movie.imageUrl,
                rating = movie.rating,
                reviewCount = movie.reviewCount,
                runningTime = movie.runningTime,
                isLiked = movie.isLiked
            )
        }

        val crossRef: ArrayList<MovieGenreCrossRef> = arrayListOf()
        val genres: ArrayList<GenreDb> = arrayListOf()

        moviesFromNetwork.forEach {
            if (it.genres?.isEmpty() == false)
                for (genre in it.genres) {
                    genres.add(GenreDb(genreId = genre.id, name = genre.name))
                    crossRef.add(MovieGenreCrossRef(movieId = it.id, genreId = genre.id))
                }
        }

        db.getMovieDao().insertMovies(movies)
        db.getGenreDao().insertGenres(genres)
        db.getMovieGenreCrossRefDao().insertCrossRef(crossRef)
    }

    override suspend fun loadMovie(movieId: Int): MovieDetails {
        val movieDetailsDb = db.getMovieDetailsDao().getMovieDetails(movieId)
        return MovieDetails(
            id = movieDetailsDb.movie.movieId,
            title = movieDetailsDb.movie.title,
            storyLine = movieDetailsDb.movie.storyLine,
            detailImageUrl = movieDetailsDb.movie.detailImageUrl,
            rating = movieDetailsDb.movie.rating,
            reviewCount = movieDetailsDb.movie.reviewCount,
            pgAge = movieDetailsDb.movie.pgAge,
            genres = movieDetailsDb.genres.map { genreDb -> Genre(id = genreDb.genreId, name = genreDb.name) },
            actors = movieDetailsDb.actors.map { actorDb -> Actor(id = actorDb.actorId, name = actorDb.name, imageUrl = actorDb.imageUrl) }
        )
    }

    override suspend fun deleteMovie(movieId: Int) {
        db.getMovieDao().deleteMovie(movieId)
    }

    override suspend fun insertMovie(movieFromNetwork: MovieDetails) {
        val movieDetails = MovieDetailsDb(
            movieId = movieFromNetwork.id,
            title = movieFromNetwork.title,
            storyLine = movieFromNetwork.storyLine,
            detailImageUrl = movieFromNetwork.detailImageUrl,
            rating = movieFromNetwork.rating,
            reviewCount = movieFromNetwork.reviewCount,
            pgAge = movieFromNetwork.pgAge
        )

        val genres: ArrayList<GenreDb> = arrayListOf()
        val actors: ArrayList<ActorDb> = arrayListOf()
        val crossRefGenres: ArrayList<MovieGenreCrossRef> = arrayListOf()
        val crossRefActors: ArrayList<MovieActorCrossRef> = arrayListOf()

        movieFromNetwork.genres?.forEach { genre ->
            genres.add(GenreDb(genreId = genre.id, name = genre.name))
            crossRefGenres.add(MovieGenreCrossRef(movieId = movieFromNetwork.id, genreId = genre.id))
        }

        movieFromNetwork.actors?.forEach { actor ->
            actors.add(ActorDb(actorId = actor.id, name = actor.name, imageUrl = actor.imageUrl))
            crossRefActors.add(MovieActorCrossRef(movieId = movieFromNetwork.id, actorId = actor.id))
        }

        db.getMovieDetailsDao().insertMovieDetails(movieDetails)
        db.getGenreDao().insertGenres(genres)
        db.getActorDao().insertActors(actors)
        db.getMovieGenreCrossRefDao().insertCrossRef(crossRefGenres)
        db.getMovieActorCrossRefDao().insertCrossRef(crossRefActors)
    }

    override suspend fun exists(movieId: Int): Boolean {
        return db.getMovieDetailsDao().exists(movieId)
    }
}