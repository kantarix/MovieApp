package com.flethy.androidacademy.model

import java.io.Serializable

data class Movie(
    val id: Int,
    val title: String,
    val imageUrl: String?,
    val rating: Int,
    val reviewCount: Int,
    val pgAge: Int,
    val runningTime: Int,
    val genres: List<Genre>? = emptyList(),
    val isLiked: Boolean
) : Serializable