package com.flethy.androidacademy.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularResponse (

    @SerialName("page")
    val page: Int,

    @SerialName("results")
    val results: List<MovieResponse>

)