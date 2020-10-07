package com.marvel.marveluniverse

import android.app.Application
import androidx.work.Configuration
import com.marvel.marveluniverse.di.components.AppComponent
import com.marvel.marveluniverse.di.components.DaggerAppComponent

class MyApp : Application(), Configuration.Provider {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().build()
    }
}
