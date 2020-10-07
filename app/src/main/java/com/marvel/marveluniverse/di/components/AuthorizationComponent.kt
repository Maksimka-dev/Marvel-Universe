package com.marvel.marveluniverse.di.components

import com.marvel.marveluniverse.ui.authorization.AuthorizationActivity
import com.marvel.marveluniverse.ui.authorization.login.LoginFragment
import com.marvel.marveluniverse.ui.authorization.registration.RegistrationFragment
import dagger.Subcomponent

@Subcomponent
interface AuthorizationComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthorizationComponent
    }

    fun inject(activity: AuthorizationActivity)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: RegistrationFragment)
}
