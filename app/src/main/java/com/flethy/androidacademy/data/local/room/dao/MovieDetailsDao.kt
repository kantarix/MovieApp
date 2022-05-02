package com.flethy.androidacademy.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flethy.androidacademy.data.local.room.entity.MovieDetailsDb
import com.flethy.androidacademy.data.local.room.entity.MovieDetailsWithGenresAndActors

@Dao
interface MovieDetailsDao {

    @Query("SELECT * FROM MovieDetails WHERE movieId == :movieId")
    fun getMovieDetails(movieId: Int): MovieDetailsWithGenresAndActors

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetails(item: MovieDetailsDb)

    @Query("SELECT EXISTS (SELECT * FROM MovieDetails WHERE movieId == :movieId)")
    fun exists(movieId: Int): Boolean

}