package com.example.smolcam

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.smolcam.databinding.ActivityGalleryBinding

class GalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGalleryBinding
    private var currentTheme: String = ""

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
        ThemeHelper.applyTheme(this)
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentTheme = ThemeHelper.getSelectedTheme(this)

        binding.galleryRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        val adapter = GalleryAdapter(allImages, imageTitles, imageDates)
        binding.galleryRecyclerView.adapter = adapter

        binding.fabCamera.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.fabSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun onResume() {
        super.onResume()
        if (currentTheme != ThemeHelper.getSelectedTheme(this)) {
            recreate()
        }
    }
}