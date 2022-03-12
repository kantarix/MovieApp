package com.flethy.androidacademy.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse (

    @SerialName("title")
    val title: String,

    @SerialName("backdrop_path")
    val backdropPath: String,

    @SerialName("genres")
    val genres: List<GenreResponse>,

    @SerialName("id")
    val id: Int,

    @SerialName("vote_count")
    val voteCount: Int,

    @SerialName("overview")
    val overview: String,

    @SerialName("runtime")
    val runtime: Int,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("adult")
    val adult: Boolean

)