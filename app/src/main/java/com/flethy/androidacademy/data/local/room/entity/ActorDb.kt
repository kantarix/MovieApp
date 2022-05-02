package com.flethy.androidacademy.data.local.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "Actor",
    primaryKeys = ["actorId"]
)
data class ActorDb (
    val actorId: Int,
    val name: String,
    val imageUrl: String?
)