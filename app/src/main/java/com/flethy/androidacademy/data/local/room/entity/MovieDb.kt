package com.flethy.androidacademy.data.local.room.entity

import androidx.room.*

@Entity(
    tableName = "Movie",
    primaryKeys = ["movieId"]
)
data class MovieDb (
    val movieId: Int,
    val title: String,
    val imageUrl: String?,
    val rating: Int,
    val reviewCount: Int,
    val pgAge: Int,
    val runningTime: Int,
    val isLiked: Boolean,
)

data class MovieWithGenres(
    @Embedded
    val movie: MovieDb,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val genres: List<GenreDb>
)