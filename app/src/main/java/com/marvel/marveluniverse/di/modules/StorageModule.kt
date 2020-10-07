package com.marvel.marveluniverse.di.modules

import com.marvel.marveluniverse.storage.SharedPreferencesStorage
import com.marvel.marveluniverse.storage.Storage
import dagger.Binds
import dagger.Module

@Module
abstract class StorageModule {

    @Binds
    abstract fun provideStorage(storage: SharedPreferencesStorage): Storage
}
