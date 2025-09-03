package com.example.smolcam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smolcam.databinding.ActivityImageViewerBinding

class ImageViewerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val images = intent.getIntegerArrayListExtra("IMAGES") ?: arrayListOf()
        val startPosition = intent.getIntExtra("POSITION", 0)

        val adapter = FullscreenImageAdapter(images)
        binding.imageViewPager.adapter = adapter
        binding.imageViewPager.setCurrentItem(startPosition, false)

        binding.backButton.setOnClickListener {
            // This will close the current activity with an animation
            supportFinishAfterTransition()
        }
    }
}