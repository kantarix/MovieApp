package com.flethy.androidacademy.data

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.flethy.androidacademy.R
import com.flethy.androidacademy.main.MainActivity
import com.flethy.androidacademy.model.Movie
import java.net.URL

interface Notifications {
    fun initialize()
    fun showNotification(movie: Movie)
    fun dismissNotification(movie: Movie)
}

class MovieNotification(private val context: Context) : Notifications {

    companion object {
        private const val CHANNEL_NEW_MOVIES = "new_movies"
        private const val REQUEST_CONTENT = 1
        private const val MOVIE_TAG = "movie"
    }

    private val notificationManagerCompat: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    override fun initialize() {
        if (notificationManagerCompat.getNotificationChannel(CHANNEL_NEW_MOVIES) == null) {
            notificationManagerCompat.createNotificationChannel(
                NotificationChannelCompat
                    .Builder(CHANNEL_NEW_MOVIES, NotificationManagerCompat.IMPORTANCE_HIGH)
                    .setName(context.getString(R.string.channel_new_movies))
                    .build()
            )
        }
    }

    override fun showNotification(movie: Movie) {

        val contentUri = "https://androidacademy.flethy.com/movie/${movie.id}".toUri()

        val movieIntent = Intent(context, MainActivity::class.java)
            .setAction(Intent.ACTION_VIEW).setData(contentUri)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .putExtra("MovieNotificationIntent", true)

        val pendingIntent = PendingIntent.getActivity(
            context,
            REQUEST_CONTENT,
            movieIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val poster = BitmapFactory.decodeStream(URL(movie.imageUrl).openConnection().getInputStream())

        val notification = NotificationCompat.Builder(context, CHANNEL_NEW_MOVIES)
            .setSmallIcon(R.drawable.ic_baseline_movie_filter_24)
            .setLargeIcon(poster)
            .setContentTitle(movie.title)
            .setContentText(context.getString(R.string.movie_notification_description, movie.rating))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setWhen(System.currentTimeMillis())
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(poster)
                    .bigLargeIcon(null)
            )
            .setContentIntent(pendingIntent)
            .build()

        notificationManagerCompat.notify(MOVIE_TAG, movie.id, notification)

    }

    override fun dismissNotification(movie: Movie) {
        notificationManagerCompat.cancel(MOVIE_TAG, movie.id)
    }

}