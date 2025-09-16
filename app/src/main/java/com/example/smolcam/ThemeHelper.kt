package com.example.smolcam

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

object ThemeHelper {
    private const val PREFS_NAME = "SmolCamPrefs"
    private const val KEY_THEME = "app_theme"
    const val THEME_ORIGINAL = "original"
    const val THEME_YELLOW_BLUE = "yellow_blue"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun applyTheme(activity: AppCompatActivity) {
        val theme = getSelectedTheme(activity)
        val themeResId = when (theme) {
            THEME_YELLOW_BLUE -> R.style.Theme_SmolCam_YellowBlue
            else -> R.style.Theme_SmolCam_Original
        }
        activity.setTheme(themeResId)
    }

    fun getSelectedTheme(context: Context): String {
        return getPreferences(context).getString(KEY_THEME, THEME_ORIGINAL) ?: THEME_ORIGINAL
    }

    fun setSelectedTheme(context: Context, theme: String) {
        getPreferences(context).edit().putString(KEY_THEME, theme).apply()
    }
}