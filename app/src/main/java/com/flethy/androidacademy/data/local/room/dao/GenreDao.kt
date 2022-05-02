package com.flethy.androidacademy.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flethy.androidacademy.data.local.room.entity.GenreDb

@Dao
interface GenreDao {

    @Query("SELECT * FROM Genre")
    fun getGenres(): List<GenreDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(items: List<GenreDb>)

}