package com.marvel.marveluniverse.db.marvel.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.marvel.marveluniverse.db.marvel.entity.CharacterEntity

@Dao
interface CharacterDao {

    @Insert
    suspend fun insertAll(characters: List<CharacterEntity>?)

    @Query("SELECT * FROM characters_table")
    suspend fun getAll(): List<CharacterEntity>

    @Query("SELECT * FROM characters_table LIMIT :fromCharacter, :countOfCharacters")
    suspend fun getNewCharacters(fromCharacter: Int, countOfCharacters: Int): List<CharacterEntity>?

    @Query("SELECT * FROM characters_table WHERE id LIKE :id")
    suspend fun getById(id: Int?): CharacterEntity
}
