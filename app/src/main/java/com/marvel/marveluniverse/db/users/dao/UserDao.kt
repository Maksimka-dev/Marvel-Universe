package com.marvel.marveluniverse.db.users.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Dao
import androidx.room.Query
import com.marvel.marveluniverse.db.users.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(user: UserEntity): Long?

    @Query("DELETE FROM user_table WHERE id LIKE :id")
    suspend fun delete(id: Long?): Int

    @Query("SELECT * FROM user_table WHERE username LIKE :userName AND password LIKE :password")
    suspend fun findByInputData(userName: String, password: String): UserEntity?

    @Query("SELECT * FROM user_table WHERE username LIKE :userName")
    suspend fun findByUsername(userName: String): UserEntity?

    @Query("SELECT * FROM user_table")
    suspend fun getAll(): List<UserEntity>
}
