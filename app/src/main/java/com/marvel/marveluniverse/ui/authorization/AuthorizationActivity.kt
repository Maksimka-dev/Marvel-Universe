package com.marvel.marveluniverse.ui.authorization

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marvel.marveluniverse.MyApp
import com.marvel.marveluniverse.R
import com.marvel.marveluniverse.databinding.ActivityAuthorizationBinding
import com.marvel.marveluniverse.di.components.AuthorizationComponent
import com.marvel.marveluniverse.ui.authorization.login.LoginFragment
import com.marvel.marveluniverse.ui.authorization.registration.RegistrationFragment
import com.marvel.marveluniverse.ui.user.main.MainActivity
import javax.inject.Inject

class AuthorizationActivity : AppCompatActivity(), AuthorizationRouter {

    @Inject
    lateinit var authorizationViewModel: AuthorizationViewModel

    lateinit var authorizationComponent: AuthorizationComponent

    lateinit var binding: ActivityAuthorizationBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        authorizationComponent = (applicationContext as MyApp).appComponent.authorizationComponent().create()
        authorizationComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.authorizationToolBar)

        defaultViewModelProviderFactory

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        binding.authorizationToolBar.setNavigationOnClickListener {
            onBackPressed()
        }

        val action = intent.getStringExtra(AuthorizationViewModel.AUTHORIZATION_KEY)

        when(action) {
            AuthorizationViewModel.LOGIN -> goToLoginFragment()
            AuthorizationViewModel.REGISTRATION -> goToRegistrationFragment()
        }
    }

    private fun goToLoginFragment() {
        supportActionBar?.title = getString(R.string.title_login_tool_bar)

        supportFragmentManager.beginTransaction()
            .replace(R.id.authorization_fragment_container, LoginFragment())
            .commit()
    }

    private fun goToRegistrationFragment() {
        supportActionBar?.title = getString(R.string.registration)

        supportFragmentManager.beginTransaction()
            .replace(R.id.authorization_fragment_container, RegistrationFragment())
            .commit()
    }

    override fun goToMainActivity() {

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
}
