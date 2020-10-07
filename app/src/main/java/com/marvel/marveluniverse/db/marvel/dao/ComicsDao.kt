package com.marvel.marveluniverse.db.marvel.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.marvel.marveluniverse.db.marvel.entity.ComicsEntity

@Dao
interface ComicsDao {

    @Insert
    suspend fun insertAll(comics: List<ComicsEntity>?)

    @Query("SELECT * FROM comics_table")
    suspend fun getAll(): List<ComicsEntity>

    @Query("SELECT * FROM comics_table LIMIT :fromComics, :countOfComics")
    suspend fun getNewComics(fromComics: Int, countOfComics: Int): List<ComicsEntity>?

    @Query("SELECT * FROM comics_table WHERE id LIKE :id")
    suspend fun getById(id: Int?): ComicsEntity
}
