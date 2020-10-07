package com.marvel.marveluniverse.ui.user.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvel.marveluniverse.ui.user.UserManager
import com.marvel.marveluniverse.utils.ConnectionController
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val userManager: UserManager,
    private val connectionController: ConnectionController
) : ViewModel() {

    private val _unregisterState = MutableLiveData<Boolean>()
    val unregisterState: LiveData<Boolean> = _unregisterState

    private val _connectionState = MutableLiveData<ViewState>()
    val connectionState: LiveData<ViewState> = _connectionState

    private val _logoutState = MutableLiveData<Boolean>()
    val logoutState: LiveData<Boolean> = _logoutState

    fun unregister() {
        viewModelScope.launch {
            _unregisterState.value = userManager.unregister()
        }
    }

    fun logout() {
        _logoutState.value = userManager.logout()
    }

    fun checkConnection(action: String) {

        val isConnection = connectionController.checkInternetConnection()

        if (isConnection) {
            _connectionState.value = ViewStateSuccess(action)
        } else {
            _connectionState.value = ViewStateError()
        }
    }

    companion object {
        const val LOGOUT_ACTION = "logout"
        const val UNREGISTER_ACTION = "unregister"
    }
}

sealed class ViewState
class ViewStateSuccess(val success: String): ViewState()
class ViewStateError: ViewState()
