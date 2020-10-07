package com.marvel.marveluniverse.di.modules

import android.content.Context
import androidx.room.Room
import com.marvel.marveluniverse.db.MarvelRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(
        context: Context
    ): MarvelRoomDatabase {
        return Room.databaseBuilder(
            context,
            MarvelRoomDatabase::class.java,
            "marvel_database"
        ).build()
    }
}
