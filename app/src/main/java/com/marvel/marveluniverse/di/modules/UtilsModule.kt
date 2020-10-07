package com.marvel.marveluniverse.di.modules

import android.content.Context
import com.marvel.marveluniverse.utils.AuthorizationTextValidator
import com.marvel.marveluniverse.utils.ConnectionController
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {

    @Singleton
    @Provides
    fun provideConnectionController(context: Context) = ConnectionController(context)

    @Singleton
    @Provides
    fun provideAuthorizationTextValidator() = AuthorizationTextValidator()
}
