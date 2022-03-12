package com.flethy.androidacademy.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse (

    @SerialName("genres")
    val genres: List<GenreResponse>

)
