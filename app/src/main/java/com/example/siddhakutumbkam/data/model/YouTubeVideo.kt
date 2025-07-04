package com.example.siddhakutumbkam.data.model

import com.google.gson.annotations.SerializedName

data class YouTubeVideo(
    val id: String,
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val publishedAt: String,
    val duration: String,
    val viewCount: String,
    val channelTitle: String
)

data class YouTubeResponse(
    val items: List<YouTubeVideoItem>
)

data class YouTubeVideoItem(
    val id: YouTubeVideoId,
    val snippet: YouTubeSnippet,
    val contentDetails: YouTubeContentDetails?,
    val statistics: YouTubeStatistics?
)

data class YouTubeVideoId(
    val videoId: String
)

data class YouTubeSnippet(
    val title: String,
    val description: String,
    val publishedAt: String,
    val channelTitle: String,
    val thumbnails: YouTubeThumbnails
)

data class YouTubeThumbnails(
    val default: YouTubeThumbnail?,
    val medium: YouTubeThumbnail?,
    val high: YouTubeThumbnail?,
    val standard: YouTubeThumbnail?,
    val maxres: YouTubeThumbnail?
)

data class YouTubeThumbnail(
    val url: String,
    val width: Int,
    val height: Int
)

data class YouTubeContentDetails(
    val duration: String
)

data class YouTubeStatistics(
    val viewCount: String?,
    val likeCount: String?,
    val commentCount: String?
)
