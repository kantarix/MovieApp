package com.flethy.androidacademy.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastResponse (

    @SerialName("name")
    val name: String,

    @SerialName("profile_path")
    val profilePath: String?,

    @SerialName("id")
    val id: Int

)
