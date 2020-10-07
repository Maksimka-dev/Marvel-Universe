package com.marvel.marveluniverse.ui.user.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.marvel.marveluniverse.MyApp
import com.marvel.marveluniverse.R
import com.marvel.marveluniverse.databinding.ActivitySettingsBinding
import com.marvel.marveluniverse.ui.user.main.MainActivity
import kotlinx.android.synthetic.main.activity_settings.*
import javax.inject.Inject

class SettingsActivity : AppCompatActivity(), SettingsInterface {

    @Inject
    lateinit var settingsViewModel: SettingsViewModel

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        (applicationContext as MyApp).appComponent.inject(this)

        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(settingsToolbar)

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        settingsToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.settings_fragment_box, SettingsFragment())
            .commit()

        settingsViewModel.logoutState.observe(this, Observer { logoutState ->

            when(logoutState) {
                true -> {
                    goToMainActivity()
                }
                false -> {
                    showSnackBar(getString(R.string.logout_error))
                }
            }
        })

        settingsViewModel.unregisterState.observe(this, Observer { unregisterState ->

            when(unregisterState) {
                true -> {
                    goToMainActivity()
                }
                else -> {
                    showSnackBar(getString(R.string.unregister_error))
                }
            }
        })

        settingsViewModel.connectionState.observe(this, Observer { connectionState ->

            when(connectionState) {
                is ViewStateSuccess -> {

                    when(connectionState.success) {
                        SettingsViewModel.LOGOUT_ACTION -> settingsViewModel.logout()
                        SettingsViewModel.UNREGISTER_ACTION -> settingsViewModel.unregister()
                    }
                }
                is ViewStateError -> {
                    showSnackBar(getString(R.string.connection_error))
                }
            }
        })
    }

    override fun logOutPressed() {
        settingsViewModel.checkConnection(SettingsViewModel.LOGOUT_ACTION)
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        finish()
    }

    override fun unregisterPressed() {
        settingsViewModel.checkConnection(SettingsViewModel.UNREGISTER_ACTION)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.root, message, Snackbar.LENGTH_LONG
        )
            .setBackgroundTint(resources.getColor(R.color.colorPrimary))
            .show()
    }
}
