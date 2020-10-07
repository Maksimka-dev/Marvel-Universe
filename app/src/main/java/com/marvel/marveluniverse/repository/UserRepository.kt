package com.marvel.marveluniverse.repository

import com.marvel.marveluniverse.db.MarvelRoomDatabase
import com.marvel.marveluniverse.db.users.entity.UserEntity
import com.marvel.marveluniverse.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val roomDatabase: MarvelRoomDatabase) {

    private val userDao = roomDatabase.userDao()

    suspend fun findUserByInputData(userName: String, password: String): User? {
        val r = userDao.findByInputData(userName, password)?.toUser()
        return r
    }

    suspend fun findUserByUserName(userName: String): User? {
        val r = userDao.findByUsername(userName)?.toUser()
        return r
    }

    suspend fun addNewUser(user: User): Long? {
        val r = userDao.add(UserEntity(user))
        return r
    }

    suspend fun deleteUser(id: Long): Boolean {
        if (userDao.delete(id) == 1) {
            return true
        }
        return false
    }

    suspend fun getAll(): List<User> {
        return userDao.getAll().map {
            it.toUser()
        }
    }


}
