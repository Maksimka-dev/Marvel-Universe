package com.marvel.marveluniverse.ui.user.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.marvel.marveluniverse.R


class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var settingsInterface: SettingsInterface

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is SettingsActivity) {
            settingsInterface = context
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findPreference<SwitchPreferenceCompat>(KEY_CHANGE_THEME)?.setOnPreferenceClickListener {

            val switch = it as SwitchPreferenceCompat
            changeTheme(switch.isChecked)
            return@setOnPreferenceClickListener true
        }

        findPreference<Preference>(KEY_LOGOUT)?.setOnPreferenceClickListener {
            settingsInterface.logOutPressed()
            return@setOnPreferenceClickListener true
        }

        findPreference<Preference>(KEY_UNREGISTER)?.setOnPreferenceClickListener {
            settingsInterface.unregisterPressed()
            return@setOnPreferenceClickListener true
        }
    }

    private fun changeTheme(isChecked: Boolean) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    companion object {
        const val KEY_CHANGE_THEME = "change_theme"
        const val KEY_LOGOUT = "logout"
        const val KEY_UNREGISTER = "unregister"
    }
}
