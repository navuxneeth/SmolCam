package com.example.smolcam

import android.content.Intent
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
        "Pink Clouds", "Misty Morning", "Warm Nights", "Cozy Study",
        "Autumn River", "City Crossing", "Retro Vibe", "Vintage Roadtrip", "Yellow Door"
    )

    private val imageDates = listOf(
        "7th Sep 2025", "7th Sep 2025", "8th Sep 2025", "8th Sep 2025",
        "8th Sep 2025", "9th Sep 2025", "9th Sep 2025", "9th Sep 2025", "9th Sep 2025"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeHelper.applyTheme(this) // Apply theme
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        val adapter = GalleryAdapter(allImages, imageTitles, imageDates)
        binding.galleryRecyclerView.adapter = adapter

        // ** FIX: Relaunch MainActivity to apply theme changes **
        binding.fabCamera.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // These flags ensure we start a fresh version of the camera screen
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // Add listener for the new settings FAB
        binding.fabSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}