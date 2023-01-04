package com.androidtask.youtubeapp.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidtask.youtubeapp.R
import com.androidtask.youtubeapp.data.models.YoutubeVideos
import com.androidtask.youtubeapp.databinding.FragmentHomeScreenBinding
import com.androidtask.youtubeapp.ui.adapters.VideosAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentHomeScreen : Fragment(R.layout.fragment_home_screen), VideoAdapterClickListeners {

    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var videosAdapter: VideosAdapter
    private val homeVideosViewModel: HomeVideosViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        hideActionBar()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observeDataFromYoutubeApi()
        transparentStatusBar()
    }

    private fun setUpRecyclerView() {
        videosAdapter = VideosAdapter(this)
        binding.videosRecyclerview.apply {
            adapter = videosAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun observeDataFromYoutubeApi() {
        homeVideosViewModel.videos.observe(viewLifecycleOwner) { result ->
            videosAdapter.differ.submitList(result.data?.items)
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @Suppress("DEPRECATION")
    private fun transparentStatusBar() {
        val window = requireActivity().window
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor =
            requireActivity().resources.getColor(R.color.white)
    }

    private fun hideActionBar() {
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onVideoClick(video: YoutubeVideos) {
        val action = FragmentHomeScreenDirections.actionHomeToFragmentVideo(video)
        findNavController().navigate(action)
    }
}