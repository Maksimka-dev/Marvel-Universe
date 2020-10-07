package com.marvel.marveluniverse.ui.user.characters.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvel.marveluniverse.model.Character
import com.marvel.marveluniverse.repository.MarvelRepository
import com.marvel.marveluniverse.utils.ConnectionController
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchCharacterViewModel @Inject constructor(
    private val repository: MarvelRepository,
    private val connectionController: ConnectionController
) : ViewModel() {

    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> = _characters

    private val _isLoadingState = MutableLiveData<Boolean>(false)
    val isLoadingState: LiveData<Boolean> = _isLoadingState

    private val _connectionState = MutableLiveData<Boolean>()
    val connectionState: LiveData<Boolean> = _connectionState

    fun getCharactersByName(name: String) {
        viewModelScope.launch {
            _isLoadingState.value = true

            val result = mutableListOf<Character>()
            var count = 0

            do {
                val list = repository
                    .getCharacterByName(count)

                Log.i("tag", "from $count")

                if (!list.isNullOrEmpty()) {
                    result.addAll(list.filter { it.name.contains(name, true) })

                    count += 100
                }

            } while (!list.isNullOrEmpty())

            _characters.value = result

            _isLoadingState.value = false
        }
    }

    fun checkConnection() {
        _connectionState.value = connectionController.checkInternetConnection()
    }
}
