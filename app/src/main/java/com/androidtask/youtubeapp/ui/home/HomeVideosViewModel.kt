package com.androidtask.youtubeapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidtask.youtubeapp.data.models.YoutubeResponse
import com.androidtask.youtubeapp.util.Resource
import com.androidtask.youtubeapp.repository.FetchVideosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeVideosViewModel @Inject constructor(
    private val repository: FetchVideosRepository
): ViewModel() {

    private val _videos: MutableLiveData<Resource<YoutubeResponse>> = MutableLiveData()
    var videos: LiveData<Resource<YoutubeResponse>> = _videos

    init {
        fetchPopularVideos()
    }

    private fun fetchPopularVideos() = viewModelScope.launch {
        _videos.postValue(Resource.Loading())
        val response = repository.getVideos()
        _videos.postValue(handleYoutubeResponse(response))
    }

    private fun handleYoutubeResponse(response: Response<YoutubeResponse>): Resource<YoutubeResponse> {
        if (response.isSuccessful) {
            response.body()?.let { youtubeResponse ->
                return Resource.Success(youtubeResponse)
            }
        }
        return Resource.Error(response.message())
    }
}