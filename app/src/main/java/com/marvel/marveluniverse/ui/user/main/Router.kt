package com.marvel.marveluniverse.ui.user.main

import android.content.Context

interface Router {

    fun goToDetailsCharacterFragment(context: Context, id: Int?)

    fun goToDetailsComicsFragment(context: Context, id: Int?)

    fun goToCharactersListFragment(context: Context)

    fun goToComicsListFragment(context: Context)

    fun goToSettingFragment(context: Context)

    fun goToSearchFragment(context: Context)

    fun goToAuthorization(context: Context, action: String)

    fun openUrl(context: Context, url: String?)

    fun configureActionBar(context: Context, value: Boolean)

    fun goStartMarvelFragment(context: Context)

    fun goToStartFragment(context: Context)
}
