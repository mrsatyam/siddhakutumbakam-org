package com.example.siddhakutumbkam.data.api

import com.example.siddhakutumbkam.data.model.YouTubeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApiService {
    
    @GET("search")
    suspend fun searchVideos(
        @Query("part") part: String = "snippet",
        @Query("channelId") channelId: String,
        @Query("maxResults") maxResults: Int = 20,
        @Query("order") order: String = "date",
        @Query("type") type: String = "video",
        @Query("key") apiKey: String
    ): Response<YouTubeResponse>
    
    @GET("videos")
    suspend fun getVideoDetails(
        @Query("part") part: String = "snippet,contentDetails,statistics",
        @Query("id") videoIds: String,
        @Query("key") apiKey: String
    ): Response<YouTubeResponse>
    
    companion object {
        const val BASE_URL = "https://www.googleapis.com/youtube/v3/"
        // Replace with actual Siddha Kutumbakam channel ID
        const val SIDDHA_KUTUMBAKAM_CHANNEL_ID = "UCChannelIdHere"
    }
}
