package com.androidtask.youtubeapp.data.api

import com.androidtask.youtubeapp.data.models.YoutubeResponse
import com.androidtask.youtubeapp.util.Constants.Companion.DETAILS
import com.androidtask.youtubeapp.util.Constants.Companion.KEY
import com.androidtask.youtubeapp.util.Constants.Companion.LIST_OF_VIDEOS
import com.androidtask.youtubeapp.util.Constants.Companion.MOST_POPULAR
import com.androidtask.youtubeapp.util.Constants.Companion.REGION_CODE
import com.androidtask.youtubeapp.util.Constants.Companion.SNIPPET
import com.androidtask.youtubeapp.util.Constants.Companion.STATISTICS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApi {

    //This endpoint gets the popular videos
    @GET(LIST_OF_VIDEOS)
    suspend fun fetchVideos(
        @Query("part") part: String = "$SNIPPET,$DETAILS,$STATISTICS",
        @Query("chart") chart: String = MOST_POPULAR,
        @Query("regionCode") regionCode: String = REGION_CODE,
        @Query("key") key: String = KEY
    ): Response<YoutubeResponse>

}