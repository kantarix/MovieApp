package com.flethy.androidacademy.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Actor(
    val id: Int,
    val name: String,
    val imageUrl: String?
) : Parcelable