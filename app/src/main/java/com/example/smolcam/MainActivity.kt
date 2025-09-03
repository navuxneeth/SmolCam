package com.example.smolcam

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.smolcam.databinding.ActivityMainBinding

enum class CameraSetting(val label: String) {
    F("F"), S("S"), EV("EV"), ISO("ISO")
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // --- State Management ---
    private var isGridVisible = true
    private var isManualMode = true
    private var currentBackgroundIndex = 0
    private var selectedSetting: CameraSetting = CameraSetting.EV

    private val backgrounds = listOf(
        R.drawable.camera_preview_background, R.drawable.background2, R.drawable.background3,
        R.drawable.background4, R.drawable.background5, R.drawable.background6,
        R.drawable.background7, R.drawable.background8, R.drawable.background9
    )

    private val settingValues = mutableMapOf(
        CameraSetting.F to "2.8",
        CameraSetting.S to "1/25",
        CameraSetting.EV to "+0.5",
        CameraSetting.ISO to "100"
    )

    private val ticksData = mapOf(
        CameraSetting.EV to listOf("-2", "-1", "0", "+1", "+2", "+3"),
        CameraSetting.F to listOf("1.4", "2.8", "4.0", "5.6", "8.0", "11.0", "16.0"),
        CameraSetting.S to listOf("1/1000", "1/500", "1/250", "1/125", "1/60", "1/30", "1/15", "1/8", "1/4", "1/2", "1"),
        CameraSetting.ISO to listOf("100", "200", "400", "800", "1600", "3200", "6400")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        setupFilterSliders()
        setupManualControls()
        updateManualControlsUI()
    }

    private fun setupClickListeners() {
        binding.gridButton.setOnClickListener {
            isGridVisible = !isGridVisible
            binding.gridView.visibility = if (isGridVisible) View.VISIBLE else View.GONE
        }

        binding.toggleControlsButton.setOnClickListener {
            isManualMode = !isManualMode
            if (isManualMode) {
                binding.controlsAnimator.displayedChild = 0
                binding.toggleControlsButton.setImageResource(R.drawable.filterbutton)
            } else {
                binding.controlsAnimator.displayedChild = 1
                binding.toggleControlsButton.setImageResource(R.drawable.ic_grid_view)
            }
        }

        binding.swapBackgroundButton.setOnClickListener {
            currentBackgroundIndex = (currentBackgroundIndex + 1) % backgrounds.size
            binding.backgroundImageView.setImageResource(backgrounds[currentBackgroundIndex])
        }

        binding.shutterButton.setOnClickListener {
            playCaptureAnimation()
        }

        // Navigate to the new GalleryActivity
        binding.arrowButton.setOnClickListener {
            val intent = Intent(this, GalleryActivity::class.java)
            startActivity(intent)
        }

        // Handle shutter button press and release for shadow effect
        binding.shutterButton.setOnTouchListener { view, event ->
            val layerDrawable = view.background as LayerDrawable
            val shadow = layerDrawable.findDrawableByLayerId(R.id.shutter_shadow)

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    shadow.alpha = 0 // Hide shadow
                    view.animate().scaleX(0.9f).scaleY(0.9f).setDuration(150).start()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    view.animate().scaleX(1f).scaleY(1f).setDuration(150).start()
                    // Delay showing the shadow again to complete the effect
                    Handler(Looper.getMainLooper()).postDelayed({
                        shadow.alpha = 255 // Show shadow
                    }, 150)
                    view.performClick() // Ensure onClickListener is triggered
                }
            }
            true // We handled the touch event
        }
    }

    private fun setupFilterSliders() {
        val filterControls = binding.controlsAnimator.findViewById<View>(R.id.filter_controls_root)
        val saturationSlider = filterControls.findViewById<SeekBar>(R.id.saturation_slider)
        val grainSlider = filterControls.findViewById<SeekBar>(R.id.grain_slider)

        saturationSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val saturationValue = progress / 50f
                updateSaturation(saturationValue)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        grainSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val grainAlpha = progress / 100f
                binding.grainOverlayImageView.alpha = grainAlpha
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun setupManualControls() {
        val manualControls = binding.controlsAnimator.getChildAt(0)
        val slider = manualControls.findViewById<InteractiveSliderView>(R.id.interactive_slider)

        slider.onValueChanged = { newValue ->
            runOnUiThread {
                settingValues[selectedSetting] = newValue
                updatePillButtons()
            }
        }
    }

    private fun updateManualControlsUI() {
        val manualControls = binding.controlsAnimator.getChildAt(0)
        val slider = manualControls.findViewById<InteractiveSliderView>(R.id.interactive_slider)

        slider.setTicks(ticksData[selectedSetting] ?: emptyList())
        updatePillButtons()
    }

    private fun updatePillButtons() {
        val manualControls = binding.controlsAnimator.getChildAt(0)
        val container = manualControls.findViewById<LinearLayout>(R.id.setting_pills_container)
        container.removeAllViews()

        for (setting in CameraSetting.values()) {
            val button = Button(
                this,
                null,
                0,
                if (setting == selectedSetting) R.style.SelectedPillButton else R.style.DefaultPillButton
            ).apply {
                text = "${setting.label}\n${settingValues[setting]}"
                isAllCaps = false
                setOnClickListener {
                    selectedSetting = setting
                    updateManualControlsUI()
                }
            }
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                marginEnd = 16
            }
            container.addView(button, params)
        }
    }

    private fun updateSaturation(value: Float) {
        val matrix = ColorMatrix()
        matrix.setSaturation(value)
        binding.backgroundImageView.colorFilter = ColorMatrixColorFilter(matrix)
    }

    private fun playCaptureAnimation() {
        ObjectAnimator.ofFloat(binding.flashOverlay, "alpha", 0f, 1f, 0f).apply {
            duration = 500
            start()
        }
    }
}