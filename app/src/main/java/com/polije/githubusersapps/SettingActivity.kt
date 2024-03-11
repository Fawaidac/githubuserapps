package com.polije.githubusersapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.polije.githubusersapps.Preferens.ThemePrefs
import com.polije.githubusersapps.Preferens.dataStore
import com.polije.githubusersapps.ViewModel.ThemeViewModel
import com.polije.githubusersapps.ViewModel.ViewModelFactory

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val switch = findViewById<SwitchMaterial>(R.id.switch_theme)
        val prefs = ThemePrefs.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ViewModelFactory(prefs)).get(
            ThemeViewModel::class.java
        )

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switch.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switch.isChecked = false
            }
        }
        switch.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSetting(isChecked)
        }
    }
}