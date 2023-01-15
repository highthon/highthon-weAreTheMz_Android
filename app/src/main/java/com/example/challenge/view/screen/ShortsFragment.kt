package com.example.challenge.view.screen

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import com.example.challenge.R
import com.example.challenge.databinding.FragmentShortsBinding

class ShortsFragment : Fragment() {
    private lateinit var binding: FragmentShortsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShortsBinding.inflate(layoutInflater)

        val uri = Uri.parse("https://mzip-s3-bucket.s3.ap-northeast-2.amazonaws.com/REELS66b1176e-919d-47b2-a1f2-f65847bafa32.mp4")
        binding.videoViewFragment.setMediaController(MediaController(context))

        binding.videoViewFragment.setVideoURI(uri)
        binding.videoViewFragment.setOnPreparedListener {
            binding.videoViewFragment.start()
        }

        return binding.root
    }
}