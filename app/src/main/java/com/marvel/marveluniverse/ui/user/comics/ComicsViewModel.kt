package com.marvel.marveluniverse.ui.user.comics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvel.marveluniverse.model.Comics
import com.marvel.marveluniverse.repository.MarvelRepository
import com.marvel.marveluniverse.utils.ConnectionController
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val COUNT_OF_COMICS = 100

class ComicsViewModel @Inject constructor(
    private val marvelRepository: MarvelRepository,
    private val connectionController: ConnectionController
) : ViewModel() {

    private var fromComicsInRoom = 0

    private val _comics = MutableLiveData<List<Comics>>()
    val comics: LiveData<List<Comics>> = _comics

    private val _isLoadingState = MutableLiveData<Boolean>()
    val isLoadingState: LiveData<Boolean> = _isLoadingState

    private val _hasLoadedAllItemsState = MutableLiveData<Boolean>()
    val hasLoadedAllItemsState: LiveData<Boolean> = _hasLoadedAllItemsState

    private val _connectionState = MutableLiveData<Boolean>()
    val connectionState: LiveData<Boolean> = _connectionState

    fun getNewComics() {
        _isLoadingState.value = true
        _hasLoadedAllItemsState.value = true

        val connection = connectionController.checkInternetConnection()

        if (connection) {
            viewModelScope.launch {

                _comics.value =
                    marvelRepository.getNewComics(fromComicsInRoom, COUNT_OF_COMICS)

                fromComicsInRoom += 100

                _isLoadingState.value = false
                _hasLoadedAllItemsState.value = false
            }
        } else {
            _connectionState.value = connection
        }
    }
}
