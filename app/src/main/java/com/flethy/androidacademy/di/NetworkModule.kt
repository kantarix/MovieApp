package com.flethy.androidacademy.di

import com.flethy.androidacademy.BuildConfig
import com.flethy.androidacademy.data.remote.retrofit.MovieApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class NetworkModule(noConnectionInterceptor: NoConnectionInterceptor) {

    private val baseUrl = "https://api.themoviedb.org/"
    private val version = "3/"

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    private val contentType = "application/json".toMediaType()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(noConnectionInterceptor)
        .addNetworkInterceptor(loggingInterceptor)
        .addInterceptor(ApiKeyInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl + version)
        .addConverterFactory(json.asConverterFactory(contentType))
        .client(httpClient)
        .build()

    val api: MovieApiService by lazy { retrofit.create(MovieApiService::class.java) }

}

class ApiKeyInterceptor: Interceptor {

    companion object {
        private const val API_KEY = BuildConfig.MOVIE_API_KEY
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        val urlBuilder = origin.url.newBuilder()
        val url = urlBuilder
            .addQueryParameter("api_key", API_KEY)
            .build()

        val requestBuilder = origin.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}