package com.flethy.androidacademy.data.local.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.flethy.androidacademy.data.local.room.dao.*
import com.flethy.androidacademy.data.local.room.entity.*

@Database(
    entities = [MovieDb::class, MovieDetailsDb::class, ActorDb::class, GenreDb::class, MovieGenreCrossRef::class, MovieActorCrossRef::class],
    version = 5
)
abstract class AppRoomDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: AppRoomDatabase? = null
        private const val DATABASE_NAME = "Movies.db"

        fun getInstance(context: Context): AppRoomDatabase {
            if (INSTANCE == null) {
                synchronized(AppRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        Log.d("DATABASE", "Creating new database instance")
                        INSTANCE = Room.databaseBuilder(
                            context,
                            AppRoomDatabase::class.java,
                            DATABASE_NAME
                        ).fallbackToDestructiveMigration().build()
                    }
                }
            }

            Log.d("DATABASE", "Getting the database instance")
            return INSTANCE!!
        }
    }

    abstract fun getMovieDao(): MovieDao
    abstract fun getMovieDetailsDao(): MovieDetailsDao
    abstract fun getGenreDao(): GenreDao
    abstract fun getActorDao(): ActorDao

    abstract fun getMovieGenreCrossRefDao(): MovieGenreCrossRefDao
    abstract fun getMovieActorCrossRefDao(): MovieActorCrossRefDao
}