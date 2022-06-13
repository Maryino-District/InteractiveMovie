package com.example.interactivemovieapp

import android.animation.ObjectAnimator
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.interactivemovieapp.databinding.ActivityMainBinding
import com.example.interactivemovieapp.databinding.ControlLayoutBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource

class MainActivity : AppCompatActivity() {
    private val videoUrl =
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4"
    private lateinit var videoPlayer: ExoPlayer
    private lateinit var playButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val overlayBinding: ControlLayoutBinding = ControlLayoutBinding.inflate(layoutInflater)
        // Create instance of exoplayer to start playing
        videoPlayer = ExoPlayer.Builder(this).build()
        binding.videoView.player = videoPlayer
        playButton = overlayBinding.button
        initVideoPlayer(videoPlayer)
        // Listener for overlay
        videoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                when (playbackState) {
                    Player.STATE_ENDED -> binding.videoView.apply {
                        // Show and capture while it end
                        useController = true
                        showController()
                        controllerShowTimeoutMs = 0
                        controllerHideOnTouch = false
                        //var myAnim: ViewAnimation = ViewAnimation(playButton, overlayBinding.)
                        //myAnim.animateAcrossScreen(playButton, overlayBinding.rootLayout, this@MainActivity)
                        playButton.x = 20 * 5 / 1000.0f + playButton.x
                        ObjectAnimator.ofFloat(binding.button, "translationX", 1000f).apply {
                            duration = 10000
                            start()
                        }
                        binding.button.text =
                            "ColorStateList.valueOf(ContextCompat.getColor(this@MainActivity, R.color.black))"
                    }
                    Player.STATE_READY -> binding.videoView.apply {
                        // Hide while its not end
                        useController = true
                        // hideController()
                        showController()


                    }
                }
            }
        })
    }

    // Preparing and initialization of player
    private fun initVideoPlayer(videoPlayer: ExoPlayer) {
        videoPlayer.apply {
            setMediaSource(buildMediaSource())
            prepare()
        }
    }

    // Initialization of mediasource
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