package com.marvel.marveluniverse.ui.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvel.marveluniverse.model.User
import com.marvel.marveluniverse.repository.UserRepository
import com.marvel.marveluniverse.ui.user.UserManager
import com.marvel.marveluniverse.utils.AuthorizationTextValidator
import com.marvel.marveluniverse.utils.ConnectionController
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val userManager: UserManager,
    private val userRepository: UserRepository,
    private val connectionController: ConnectionController,
    private val authorizationTextValidator: AuthorizationTextValidator
) : ViewModel() {

    private val _inputState = MutableLiveData<AuthorizationViewState>()
    val inputState: LiveData<AuthorizationViewState> = _inputState

    private val _connectionState = MutableLiveData<AuthorizationViewState>()
    val connectionState: LiveData<AuthorizationViewState> = _connectionState

    private val _checkUserInDatabaseState = MutableLiveData<AuthorizationViewState>()
    val checkUserInDatabaseState: LiveData<AuthorizationViewState> = _checkUserInDatabaseState

    private val _loginState = MutableLiveData<AuthorizationViewState>()
    val loginState: LiveData<AuthorizationViewState> = _loginState

    private val _registrationState = MutableLiveData<AuthorizationViewState>()
    val registrationState: LiveData<AuthorizationViewState> = _registrationState

    private val _checkUserNameState = MutableLiveData<AuthorizationViewState>()
    val checkUserNameState: LiveData<AuthorizationViewState> = _checkUserNameState

    fun validateInput(userName: String, password: String) {

        when {
            !authorizationTextValidator.isValidByLength(userName) -> {
                _inputState.value = InputError(USERNAME_LENGTH)
            }
            !authorizationTextValidator.isValidInput(userName) -> {
                _inputState.value = InputError(USERNAME_ERROR)
            }
            !authorizationTextValidator.isValidByLength(password) -> {
                _inputState.value = InputError(PASSWORD_LENGTH)
            }
            !authorizationTextValidator.isValidInput(password) -> {
                _inputState.value = InputError(PASSWORD_ERROR)
            }
            else -> {
                _inputState.value = AuthorizationSuccess()
            }
        }
    }

    fun checkUserInDatabase(userName: String) {
        viewModelScope.launch {
            val user = userRepository.findUserByUserName(userName)

            if (user != null) {
                userManager.setIdCurrentUser(user.id.toString())
                _checkUserInDatabaseState.value = AuthorizationSuccess()
            } else {
                _checkUserInDatabaseState.value = AuthorizationError()
            }
        }
    }

    fun login(userName: String, password: String) {
        viewModelScope.launch {
            val response = userRepository.findUserByInputData(userName, password)
            if (response != null) {
                userManager.loginUser(userName, password)
                _loginState.value = AuthorizationSuccess()
            } else {
                _loginState.value = AuthorizationError()
            }
        }
    }

    fun registerUser(userName: String, password: String) {
        viewModelScope.launch {
            val response = userManager.registerUser(User(null, userName, password))

            when (response) {
                true -> {
                    _registrationState.value = AuthorizationSuccess()
                }
                else -> {
                    _registrationState.value = AuthorizationError()
                }
            }
        }
    }

    fun checkUserName(userName: String) {
        viewModelScope.launch {

            val user = userRepository.findUserByUserName(userName)

            if (user != null) {
                _checkUserNameState.value =
                    AuthorizationError()
            } else {
                _checkUserNameState.value = AuthorizationSuccess()
            }
        }
    }

    fun checkConnection() {

        val isConnection = connectionController.checkInternetConnection()

        if (isConnection) {
            _connectionState.value = AuthorizationSuccess()
        } else {
            _connectionState.value = AuthorizationError()
        }
    }

    companion object {
        const val AUTHORIZATION_KEY = "authorization_key"
        const val LOGIN = "login"
        const val REGISTRATION = "registration"
        const val USERNAME_LENGTH = "username_length"
        const val USERNAME_ERROR = "username_error"
        const val PASSWORD_LENGTH = "password_length"
        const val PASSWORD_ERROR = "password_error"
    }
}

sealed class AuthorizationViewState
class AuthorizationSuccess : AuthorizationViewState()
class AuthorizationError : AuthorizationViewState()
data class InputError(val view: String) : AuthorizationViewState()
