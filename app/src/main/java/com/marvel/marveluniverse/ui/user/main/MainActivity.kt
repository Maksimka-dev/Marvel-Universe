package com.marvel.marveluniverse.ui.user.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.marvel.marveluniverse.MyApp
import com.marvel.marveluniverse.R
import com.marvel.marveluniverse.ui.user.*
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : MainRouter() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_tool_bar)

        userManager = (application as MyApp).appComponent.userManager()

        if (userManager.usernameCurrentUser == "") {

            goToStartFragment(this)


        } else {
            userManager.userJustLoggedIn()
            userManager.userComponent?.inject(this)

            if (savedInstanceState == null) {

                goStartMarvelFragment(this)
            }

            mainViewModel.themeState.observe(this, Observer {

                when (it) {
                    is ThemeStateSuccess -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    is ThemeStateError -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
            })
            mainViewModel.checkTheme()
        }
    }
}
