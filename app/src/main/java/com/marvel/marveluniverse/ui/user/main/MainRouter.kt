package com.marvel.marveluniverse.ui.user.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marvel.marveluniverse.R
import com.marvel.marveluniverse.ui.authorization.AuthorizationActivity
import com.marvel.marveluniverse.ui.authorization.AuthorizationViewModel
import com.marvel.marveluniverse.ui.user.characters.CharactersListFragment
import com.marvel.marveluniverse.ui.user.characters.details.CharacterDetailsFragment
import com.marvel.marveluniverse.ui.user.characters.search.SearchCharacterFragment
import com.marvel.marveluniverse.ui.user.comics.ComicsListFragment
import com.marvel.marveluniverse.ui.user.comics.details.ComicsDetailsFragment
import com.marvel.marveluniverse.ui.user.settings.SettingsActivity
import com.marvel.marveluniverse.ui.user.startfragment.StartFragment
import com.marvel.marveluniverse.ui.user.startmarvel.StartMarvelFragment
import kotlinx.android.synthetic.main.activity_main.*

open class MainRouter : AppCompatActivity(), Router {

    override fun goToDetailsCharacterFragment(context: Context, id: Int?) {

        (context as MainActivity)
            .supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                CharacterDetailsFragment.createInstance(id)
            )
            .addToBackStack(null)
            .commit()
    }

    override fun goToDetailsComicsFragment(context: Context, id: Int?) {

        (context as MainActivity)
            .supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                ComicsDetailsFragment.createInstance(id)
            )
            .addToBackStack(null)
            .commit()
    }

    override fun goToCharactersListFragment(context: Context) {

        (context as MainActivity)
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CharactersListFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun goToComicsListFragment(context: Context) {

        (context as MainActivity)
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, ComicsListFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun goToSettingFragment(context: Context) {
        val intent = Intent(context, SettingsActivity::class.java)
        context.startActivity(intent)
    }

    override fun goToSearchFragment(context: Context) {

        (context as MainActivity)
            .supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                SearchCharacterFragment()
            )
            .addToBackStack(null)
            .commit()
    }

    override fun goToAuthorization(context: Context, action: String) {
        val bundle = Bundle()
        bundle.putString(AuthorizationViewModel.AUTHORIZATION_KEY, action)

        val intent = Intent(context, AuthorizationActivity::class.java)
        intent.putExtras(bundle)

        context.startActivity(intent)
    }

    override fun openUrl(context: Context, url: String?) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    override fun configureActionBar(context: Context, value: Boolean) {
        (context as MainActivity).supportActionBar?.setHomeButtonEnabled(value)
        context.supportActionBar?.setDisplayHomeAsUpEnabled(value)

        if (value) {
            context.main_tool_bar.setNavigationOnClickListener {
                context.onBackPressed()
            }
        }
    }

    override fun goStartMarvelFragment(context: Context) {
        (context as MainActivity)
            .supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, StartMarvelFragment())
            .commit()
    }

    override fun goToStartFragment(context: Context) {
        (context as MainActivity)
            .supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, StartFragment())
            .commit()
    }
}
