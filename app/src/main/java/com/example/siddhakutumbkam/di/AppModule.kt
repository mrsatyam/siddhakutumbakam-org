package com.example.siddhakutumbkam.di

import com.example.siddhakutumbkam.data.api.YouTubeApiService
import com.example.siddhakutumbkam.data.repository.RetreatRepository
import com.example.siddhakutumbkam.data.repository.YouTubeRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AppModule {
    
    // YouTube API Key - Replace with actual key
    private const val YOUTUBE_API_KEY = "YOUR_YOUTUBE_API_KEY_HERE"
    
    private val httpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(YouTubeApiService.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    val youTubeApiService: YouTubeApiService by lazy {
        retrofit.create(YouTubeApiService::class.java)
    }
    
    val youTubeRepository: YouTubeRepository by lazy {
        YouTubeRepository(youTubeApiService, YOUTUBE_API_KEY)
    }
    
    val retreatRepository: RetreatRepository by lazy {
        RetreatRepository()
    }
}
