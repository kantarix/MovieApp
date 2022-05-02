package com.flethy.androidacademy.data.local.room.entity

import androidx.room.*

@Entity(
    tableName = "MovieDetails",
    primaryKeys = ["movieId"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDb::class,
            parentColumns = ["movieId"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MovieDetailsDb (
    val movieId: Int,
    val title: String,
    val storyLine: String,
    val detailImageUrl: String?,
    val rating: Int,
    val reviewCount: Int,
    val pgAge: Int
)


data class MovieDetailsWithGenresAndActors(
    @Embedded
    val movie: MovieDetailsDb,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val genres: List<GenreDb>,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "actorId",
        associateBy = Junction(MovieActorCrossRef::class)
    )
    val actors: List<ActorDb>
)