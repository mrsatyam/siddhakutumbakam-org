package com.example.siddhakutumbkam.data.repository

import com.example.siddhakutumbkam.data.api.YouTubeApiService
import com.example.siddhakutumbkam.data.model.YouTubeVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class YouTubeRepository(
    private val apiService: YouTubeApiService,
    private val apiKey: String
) {
    
    suspend fun getLatestVideos(): Result<List<YouTubeVideo>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.searchVideos(
                channelId = YouTubeApiService.SIDDHA_KUTUMBAKAM_CHANNEL_ID,
                apiKey = apiKey
            )
            
            if (response.isSuccessful) {
                val videos = response.body()?.items?.map { item ->
                    YouTubeVideo(
                        id = item.id.videoId,
                        title = item.snippet.title,
                        description = item.snippet.description,
                        thumbnailUrl = item.snippet.thumbnails.high?.url 
                            ?: item.snippet.thumbnails.medium?.url 
                            ?: item.snippet.thumbnails.default?.url 
                            ?: "",
                        publishedAt = item.snippet.publishedAt,
                        duration = item.contentDetails?.duration ?: "",
                        viewCount = item.statistics?.viewCount ?: "0",
                        channelTitle = item.snippet.channelTitle
                    )
                } ?: emptyList()
                
                Result.success(videos)
            } else {
                Result.failure(Exception("Failed to fetch videos: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getVideoDetails(videoIds: List<String>): Result<List<YouTubeVideo>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getVideoDetails(
                videoIds = videoIds.joinToString(","),
                apiKey = apiKey
            )
            
            if (response.isSuccessful) {
                val videos = response.body()?.items?.map { item ->
                    YouTubeVideo(
                        id = item.id.videoId,
                        title = item.snippet.title,
                        description = item.snippet.description,
                        thumbnailUrl = item.snippet.thumbnails.high?.url 
                            ?: item.snippet.thumbnails.medium?.url 
                            ?: item.snippet.thumbnails.default?.url 
                            ?: "",
                        publishedAt = item.snippet.publishedAt,
                        duration = item.contentDetails?.duration ?: "",
                        viewCount = item.statistics?.viewCount ?: "0",
                        channelTitle = item.snippet.channelTitle
                    )
                } ?: emptyList()
                
                Result.success(videos)
            } else {
                Result.failure(Exception("Failed to fetch video details: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
