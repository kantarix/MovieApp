package com.flethy.androidacademy.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
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
) : Parcelable