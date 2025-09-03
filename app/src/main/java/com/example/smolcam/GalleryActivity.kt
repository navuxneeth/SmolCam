package com.example.smolcam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.smolcam.databinding.ActivityGalleryBinding

class GalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGalleryBinding

    private val allImages = listOf(
        R.drawable.camera_preview_background, R.drawable.background2, R.drawable.background3,
        R.drawable.background4, R.drawable.background5, R.drawable.background6,
        R.drawable.background7, R.drawable.background8, R.drawable.background9
    )

    private val imageTitles = listOf(
        "Pink Clouds", "Blue Sky", "Deep Forest", "Misty Mountains",
        "Golden Hour", "City Lights", "Beach Sunset", "Winter Snow", "Desert Sands"
    )

    // New list of dates
    private val imageDates = listOf(
        "7th Sep 2025", "7th Sep 2025", "8th Sep 2025", "8th Sep 2025",
        "8th Sep 2025", "9th Sep 2025", "9th Sep 2025", "9th Sep 2025", "9th Sep 2025"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        // Pass the new dates list to the adapter
        val adapter = GalleryAdapter(allImages, imageTitles, imageDates)
        binding.galleryRecyclerView.adapter = adapter

        binding.fabCamera.setOnClickListener {
            finish()
        }
    }
}