package com.androidtask.youtubeapp.ui.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.androidtask.youtubeapp.data.models.YoutubeVideos
import com.androidtask.youtubeapp.databinding.ItemVideoBinding
import com.androidtask.youtubeapp.ui.home.VideoAdapterClickListeners
import com.androidtask.youtubeapp.util.Utils.convert
import com.androidtask.youtubeapp.util.Utils.viewsCount

class VideosAdapter(private val clickListeners: VideoAdapterClickListeners) : RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {
    inner class VideoViewHolder(val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<YoutubeVideos>() {
        override fun areItemsTheSame(oldItem: YoutubeVideos, newItem: YoutubeVideos) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: YoutubeVideos, newItem: YoutubeVideos) =
            oldItem == oldItem
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val itemPopularVideoBinding = ItemVideoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VideoViewHolder(itemPopularVideoBinding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = differ.currentList[position]
        val thumbnailUrl = video.snippet.thumbnails.medium.url
        val channelLogo = video.snippet.thumbnails.medium.url
        val title = video.snippet.title
        val channel = video.snippet.channelTitle
        val videoViews = viewsCount(video.statistics.viewCount.toInt())
        val publishedAt = convert(video.snippet.publishedAt)

        holder.binding.apply {
            videoThumbnail.load(thumbnailUrl)
            channelPicture.load(channelLogo){
                transformations(CircleCropTransformation())
            }
            videoTitle.text = title
            channelName.text = channel
            views.text = videoViews
            publishedTime.text = publishedAt
            this.root.setOnClickListener {
                clickListeners.onVideoClick(video)
            }
        }
    }





    override fun getItemCount() = differ.currentList.size
}