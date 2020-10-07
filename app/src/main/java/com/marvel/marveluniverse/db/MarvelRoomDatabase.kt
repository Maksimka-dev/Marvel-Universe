package com.marvel.marveluniverse.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marvel.marveluniverse.db.marvel.dao.CharacterDao
import com.marvel.marveluniverse.db.marvel.dao.ComicsDao
import com.marvel.marveluniverse.db.marvel.entity.CharacterEntity
import com.marvel.marveluniverse.db.marvel.entity.ComicsEntity
import com.marvel.marveluniverse.db.users.dao.UserDao
import com.marvel.marveluniverse.db.users.entity.UserEntity

@Database(
    entities = [CharacterEntity::class, ComicsEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MarvelRoomDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    abstract fun comicsDao(): ComicsDao

    abstract fun userDao(): UserDao
}
