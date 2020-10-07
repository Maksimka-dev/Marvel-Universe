package com.marvel.marveluniverse.di.components

import android.content.Context
import com.marvel.marveluniverse.di.modules.*
import com.marvel.marveluniverse.repository.MarvelRepository
import com.marvel.marveluniverse.repository.UserRepository
import com.marvel.marveluniverse.ui.user.UserManager
import com.marvel.marveluniverse.ui.user.settings.SettingsActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppSubComponents::class,
        DatabaseModule::class,
        NetworkModule::class,
        StorageModule::class,
        UtilsModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun authorizationComponent(): AuthorizationComponent.Factory
    fun userRepository(): UserRepository
    fun userManager(): UserManager
    fun marvelRepository(): MarvelRepository

    fun inject(activity: SettingsActivity)
}
