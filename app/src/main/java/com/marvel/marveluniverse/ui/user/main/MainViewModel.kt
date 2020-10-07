package com.marvel.marveluniverse.ui.user.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.marvel.marveluniverse.di.scope.LoggedUserScope
import com.marvel.marveluniverse.ui.user.settings.SettingsFragment
import javax.inject.Inject

@LoggedUserScope
class MainViewModel @Inject constructor(
    private val context: Context
) : ViewModel() {

    private val _themeState = MutableLiveData<ThemeState>()
    val themeState: LiveData<ThemeState> = _themeState

    fun checkTheme() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        if (sharedPreferences.getBoolean(SettingsFragment.KEY_CHANGE_THEME, false)) {
            _themeState.value = ThemeStateSuccess()
        } else {
            _themeState.value = ThemeStateError()
        }
    }
}

sealed class ThemeState
class ThemeStateSuccess: ThemeState()
class ThemeStateError: ThemeState()
