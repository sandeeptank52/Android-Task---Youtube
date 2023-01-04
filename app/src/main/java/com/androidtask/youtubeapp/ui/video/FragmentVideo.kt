package com.androidtask.youtubeapp.ui.video

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.androidtask.youtubeapp.data.models.YoutubeVideos
import com.androidtask.youtubeapp.databinding.FragmentVideoBinding
import com.androidtask.youtubeapp.util.Utils
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController


class FragmentVideo : Fragment(), YouTubePlayer.OnInitializedListener {


    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!
    private var videoData: YoutubeVideos? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: FragmentVideoArgs by navArgs()
        videoData = args.videoData
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewsAndData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViewsAndData() {
        setUpVideoView()
        binding.videoTitleInVideoScreen.text = videoData?.snippet?.title
        binding.channelNameVideoData.text = videoData?.snippet?.channelTitle
        binding.channelSubscribersVideoData.text = videoData?.snippet?.channelTitle
        binding.videoViews.text = Utils.viewsCount(videoData?.statistics?.viewCount?.toInt() ?: 0)
        binding.videoPublishedDate.text = Utils.convert(videoData?.snippet?.publishedAt ?: "")
        binding.channelLogoVideoData
            .load(videoData?.snippet?.thumbnails?.medium?.url) {
                transformations(CircleCropTransformation())
            }

    }

    private fun setUpVideoView() {
        val youTubePlayerView = binding.youtubePlayerView
        youTubePlayerView.enableAutomaticInitialization = false

        val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer) {
                super.onReady(youTubePlayer)
                val defaultPlayerUiController =
                    DefaultPlayerUiController(youTubePlayerView, youTubePlayer)
                defaultPlayerUiController.showFullscreenButton(true)
                defaultPlayerUiController.setFullScreenButtonClickListener {
                    if (youTubePlayerView.isFullScreen()) {
                        youTubePlayerView.exitFullScreen()
                        requireActivity().window.decorView.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_VISIBLE
                    } else {
                        youTubePlayerView.enterFullScreen()
                        requireActivity().window.decorView.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_FULLSCREEN

                    }
                }


                youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)

                val videoId = videoData?.id ?: ""
                youTubePlayer.cueVideo(videoId, 0f)
            }
        }

        // Disable iFrame UI
        val options: IFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()
        youTubePlayerView.initialize(listener, options)
    }


    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        p1?.loadVideo(videoData?.id)
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {

    }

}