package com.flethy.androidacademy.data.models

import java.io.Serializable

data class Movie(
    val title: String,
    val logo: String,
    val ageRestriction: Int,
    val like: Boolean,
    val genres: String,
    val rating: Double,
    val reviewCount: Int,
    val duration: Int,
    val storyline: String,
    val actors: List<Actor>
) : Serializable