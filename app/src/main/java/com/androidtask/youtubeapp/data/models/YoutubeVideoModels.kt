package com.androidtask.youtubeapp.data.models

import java.io.Serializable

data class ContentDetails(
    val duration: String
) : Serializable

data class ThumbnailAtrributes(
    val url: String
) : Serializable

data class Thumbnails(
    val medium: ThumbnailAtrributes
) : Serializable

data class VideoDetails(
    val publishedAt: String,
    val title: String,
    val thumbnails: Thumbnails,
    val channelTitle: String
) : Serializable

data class VideoStats(
    val viewCount: String,
    val likeCount: String
) : Serializable

data class YoutubeResponse(
    val items: List<YoutubeVideos>
) : Serializable

data class YoutubeVideos(
    val id: String,
    val snippet: VideoDetails,
    val contentDetails: ContentDetails,
    val statistics: VideoStats
) : Serializable
