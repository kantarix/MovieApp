package com.flethy.androidacademy.data.local.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(
    tableName = "Genre",
    primaryKeys = ["genreId"]
)
data class GenreDb (
    val genreId: Int,
    val name: String
)