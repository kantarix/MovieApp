package com.flethy.androidacademy.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCastResponse (

    @SerialName("cast")
    val cast: List<CastResponse>,

    @SerialName("id")
    val id: Int

)