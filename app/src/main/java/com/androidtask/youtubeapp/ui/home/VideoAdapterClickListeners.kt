package com.androidtask.youtubeapp.ui.home

import com.androidtask.youtubeapp.data.models.YoutubeVideos

interface VideoAdapterClickListeners {
    fun onVideoClick(video: YoutubeVideos)
}