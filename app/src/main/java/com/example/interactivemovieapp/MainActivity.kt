package com.example.interactivemovieapp

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.interactivemovieapp.databinding.ActivityMainBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import java.util.*

class MainActivity : AppCompatActivity() {
    private val videoUrl =
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4"
    private lateinit var videoPlayer: ExoPlayer
    //private var videoUrl = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        // create instance of exoplayer to start playing
        videoPlayer = ExoPlayer.Builder(this).build()
        // assign it to the view
        binding.videoView.player = videoPlayer
        initVideoPlayer(videoPlayer)
        videoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                when (playbackState)  {
                    Player.STATE_ENDED -> binding.videoView.apply {
                        useController = true
                        showController()
                        controllerShowTimeoutMs = 0
                        controllerHideOnTouch = false
                    }
                    Player.STATE_READY -> binding.videoView.apply {
                        useController = false
                        hideController()
                    }
                }
            }
        })
    }

    private fun initVideoPlayer(videoPlayer: ExoPlayer) {
        videoPlayer.apply {
            setMediaSource(buildMediaSource())
            prepare()
        }
    }

    private fun buildMediaSource(): MediaSource {
        val dataSourceFactory = DefaultDataSource.Factory(this)
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Uri.parse(videoUrl)))
    }

    override fun onResume() {
        super.onResume()
        videoPlayer.playWhenReady = true
    }

    override fun onStop() {
        super.onStop()
        videoPlayer.release()
    }
}