package com.marvel.marveluniverse.ui.user.comics.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvel.marveluniverse.model.Comics
import com.marvel.marveluniverse.repository.MarvelRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ComicsDetailsViewModel @Inject constructor(
    private val marvelRepository: MarvelRepository
) : ViewModel() {

    private val _comicsByIdState = MutableLiveData<Comics>()
    val comicsByIdState: LiveData<Comics> = _comicsByIdState

    fun getComicsById(id: Int?) {
        viewModelScope.launch {
            _comicsByIdState.value = marvelRepository.getComicsById(id)
        }
    }
}
