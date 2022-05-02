package com.flethy.androidacademy

import android.app.Application
import com.flethy.androidacademy.data.local.room.AppRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieApp : Application() {

    companion object {
        lateinit var db: AppRoomDatabase
    }

    override fun onCreate() {
        super.onCreate()
        db = AppRoomDatabase.getInstance(this)
//        GlobalScope.launch(Dispatchers.IO) {
//            db.clearAllTables()
//        }

    }

}