package com.marvel.marveluniverse.ui.user

import com.marvel.marveluniverse.di.components.UserComponent
import com.marvel.marveluniverse.model.User
import com.marvel.marveluniverse.repository.UserRepository
import com.marvel.marveluniverse.storage.Storage
import javax.inject.Inject
import javax.inject.Singleton

private const val ID = "id"
private const val USERNAME = "username"

@Singleton
class UserManager @Inject constructor(
    private val storage: Storage,
    private val userRepository: UserRepository,
    private val userComponentFactory: UserComponent.Factory
) {
    val usernameCurrentUser: String
        get() = storage.getString(USERNAME)

    var userComponent: UserComponent? = null
        private set

    fun setIdCurrentUser(id: String) {
        storage.setString(ID, id)
    }

    suspend fun registerUser(user: User): Boolean {
        val id = userRepository.addNewUser(user)

        with(storage) {
            setString(ID, id.toString())
            setString(USERNAME, user.userName)
        }

        if (id != null) {
            return true
        }
        return false
    }

    fun loginUser(username: String, password: String): Boolean {
        with(storage) {
            setString(USERNAME, username)
        }
        return true
    }

    fun logout(): Boolean {
        with(storage) {
            setString(ID, "")
            setString(USERNAME, "")
        }
        userComponent = null
        return true
    }

    suspend fun unregister(): Boolean {
        val userId = storage.getString(ID).toLong()

        if (userRepository.deleteUser(userId)) {
            with(storage) {
                setString(ID, "")
                setString(USERNAME, "")
            }
            userComponent = null
            return true
        }

        return false
    }

    fun userJustLoggedIn() {
        userComponent = userComponentFactory.create()
    }
}
