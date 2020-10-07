package com.marvel.marveluniverse.ui.user.characters.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvel.marveluniverse.model.Character
import com.marvel.marveluniverse.repository.MarvelRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(private val marvelRepository: MarvelRepository) :
    ViewModel() {

    private val _characterByIdState = MutableLiveData<Character>()
    val characterByIdState: LiveData<Character> = _characterByIdState

    fun getCharacterById(id: Int?) {
        viewModelScope.launch {
            _characterByIdState.value = marvelRepository.getCharacterById(id)
        }
    }
}
