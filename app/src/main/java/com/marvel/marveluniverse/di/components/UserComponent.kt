package com.marvel.marveluniverse.di.components

import com.marvel.marveluniverse.di.scope.LoggedUserScope
import com.marvel.marveluniverse.ui.user.main.MainActivity
import com.marvel.marveluniverse.ui.user.characters.CharactersListFragment
import com.marvel.marveluniverse.ui.user.characters.search.SearchCharacterFragment
import com.marvel.marveluniverse.ui.user.characters.details.CharacterDetailsFragment
import com.marvel.marveluniverse.ui.user.comics.ComicsListFragment
import com.marvel.marveluniverse.ui.user.comics.details.ComicsDetailsFragment
import dagger.Subcomponent

@LoggedUserScope
@Subcomponent
interface UserComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: CharactersListFragment)
    fun inject(fragmentCharacter: CharacterDetailsFragment)
    fun inject(fragment: ComicsListFragment)
    fun inject(fragment: ComicsDetailsFragment)
    fun inject(fragment: SearchCharacterFragment)
}
