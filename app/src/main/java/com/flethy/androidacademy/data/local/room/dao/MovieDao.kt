package com.flethy.androidacademy.data.local.room.dao

import androidx.room.*
import com.flethy.androidacademy.data.local.room.entity.*

@Dao
interface MovieDao {

    @Transaction
    @Query("SELECT * FROM Movie")
    fun getMovies(): List<MovieWithGenres>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(items: List<MovieDb>)

    @Query("DELETE FROM Movie WHERE movieId == :movieId")
    fun deleteMovie(movieId: Int)

}

@Dao
interface MovieGenreCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrossRef(items: List<MovieGenreCrossRef>)

}

@Dao
interface MovieActorCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrossRef(items: List<MovieActorCrossRef>)

}