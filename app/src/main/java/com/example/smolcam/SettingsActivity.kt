package com.example.smolcam

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.smolcam.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply theme before super.onCreate() and setContentView()
        ThemeHelper.applyTheme(this)
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        updateButtonColors()
    }

    private fun updateButtonColors() {
        val currentTheme = ThemeHelper.getSelectedTheme(this)
        if (currentTheme == ThemeHelper.THEME_YELLOW_BLUE) {
            val blueColor = ContextCompat.getColor(this, R.color.yb_primary)
            val whiteColor = ContextCompat.getColor(this, R.color.yb_on_primary)
            binding.contactButton.backgroundTintList = ColorStateList.valueOf(blueColor)
            binding.visitHahoButton.backgroundTintList = ColorStateList.valueOf(blueColor)
            binding.contactButton.setTextColor(whiteColor)
            binding.visitHahoButton.setTextColor(whiteColor)
        } else {
            val redColor = getThemeColor(R.attr.smolCamAccent)
            val creamColor = getThemeColor(R.attr.smolCamOnPrimary)
            binding.contactButton.backgroundTintList = ColorStateList.valueOf(redColor)
            binding.visitHahoButton.backgroundTintList = ColorStateList.valueOf(redColor)
            binding.contactButton.setTextColor(creamColor)
            binding.visitHahoButton.setTextColor(creamColor)
        }
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.themeOriginalButton.setOnClickListener {
            ThemeHelper.setSelectedTheme(this, ThemeHelper.THEME_ORIGINAL)
            recreate() // Recreate activity to apply the new theme
        }

        binding.themeYellowBlueButton.setOnClickListener {
            ThemeHelper.setSelectedTheme(this, ThemeHelper.THEME_YELLOW_BLUE)
            recreate() // Recreate activity to apply the new theme
        }

        binding.contactButton.setOnClickListener {
            val url = "https://www.linkedin.com/in/navaneeth-sankar-k-p/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        binding.visitHahoButton.setOnClickListener {
            val url = "https://www.figma.com/design/b09QjOaFQ0OX14deXgcPOJ/HaHo-%E2%80%94-Mobile-UI-Kit---Multibrand-Design-System?node-id=4668-7848"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun getThemeColor(@AttrRes attrRes: Int): Int {
        val typedValue = TypedValue()
        theme.resolveAttribute(attrRes, typedValue, true)
        return ContextCompat.getColor(this, typedValue.resourceId)
    }
}