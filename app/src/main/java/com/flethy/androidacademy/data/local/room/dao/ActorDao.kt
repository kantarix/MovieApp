package com.flethy.androidacademy.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flethy.androidacademy.data.local.room.entity.ActorDb
import com.flethy.androidacademy.data.local.room.entity.GenreDb

@Dao
interface ActorDao {

    @Query("SELECT * FROM Actor")
    fun getActors(): List<ActorDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertActors(items: List<ActorDb>)

}