package com.marvel.marveluniverse.ui.user.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvel.marveluniverse.model.Character
import com.marvel.marveluniverse.repository.MarvelRepository
import com.marvel.marveluniverse.utils.ConnectionController
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val COUNT_OF_CHARACTERS = 100

class CharactersListViewModel @Inject constructor(
    private val marvelRepository: MarvelRepository,
    private val connectionController: ConnectionController
) : ViewModel() {

    private var fromCharacterInRoom = 0

    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> = _characters

    private val _isLoadingState = MutableLiveData<Boolean>()
    val isLoadingState: LiveData<Boolean> = _isLoadingState

    private val _hasLoadedAllItemsState = MutableLiveData<Boolean>()
    val hasLoadedAllItemsState: LiveData<Boolean> = _hasLoadedAllItemsState

    private val _connectionState = MutableLiveData<Boolean>()
    val connectionState: LiveData<Boolean> = _connectionState

    fun getNewCharacters() {
        _isLoadingState.value = true
        _hasLoadedAllItemsState.value = true

        val connection = connectionController.checkInternetConnection()

        if (connection) {
            viewModelScope.launch {

                _characters.value =
                    marvelRepository.getNewCharacters(fromCharacterInRoom, COUNT_OF_CHARACTERS)

                fromCharacterInRoom += 100

                _isLoadingState.value = false
                _hasLoadedAllItemsState.value = false

            }
        } else {
            _connectionState.value = connection
        }
    }
}
