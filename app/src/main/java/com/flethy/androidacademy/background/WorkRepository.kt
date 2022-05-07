package com.flethy.androidacademy.background

import androidx.work.*
import java.util.concurrent.TimeUnit

class WorkRepository {

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresCharging(true)
        .build()

    val preloadRequest = PeriodicWorkRequest.Builder(PreloadWorker::class.java, 8L, TimeUnit.HOURS)
        .setConstraints(constraints)
        .build()

}