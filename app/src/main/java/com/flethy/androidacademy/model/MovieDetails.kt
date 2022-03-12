package com.flethy.androidacademy.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val storyLine: String,
    val detailImageUrl: String?,
    val rating: Int,
    val reviewCount: Int,
    val pgAge: Int,
    val genres: List<Genre>? = emptyList(),
    val actors: List<Actor>? = emptyList(),
)