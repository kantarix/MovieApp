package com.flethy.androidacademy.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse (

    @SerialName("name")
    val name: String,

    @SerialName("id")
    val id: Int

)