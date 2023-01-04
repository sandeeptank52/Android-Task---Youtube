package com.androidtask.youtubeapp.repository

import com.androidtask.youtubeapp.data.api.YoutubeApi
import com.androidtask.youtubeapp.data.models.YoutubeResponse
import retrofit2.Response
import javax.inject.Inject

class FetchVideosRepository @Inject constructor(
    private val api: YoutubeApi
) {

    suspend fun getVideos(): Response<YoutubeResponse> {
        return api.fetchVideos()
    }

}