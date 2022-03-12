package com.flethy.androidacademy.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationResponse (

    @SerialName("images")
    val images: ImageResponse

)