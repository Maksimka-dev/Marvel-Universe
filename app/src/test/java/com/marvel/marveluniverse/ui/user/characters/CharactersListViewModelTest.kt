package com.marvel.marveluniverse.ui.user.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.marvel.marveluniverse.repository.MarvelRepository
import com.marvel.marveluniverse.utils.ConnectionController
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharactersListViewModelTest {

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()

    private lateinit var marvelRepository: MarvelRepository
    private lateinit var charactersListViewModel: CharactersListViewModel
    private lateinit var connectionController: ConnectionController
    private lateinit var observer: Observer<Boolean>

    @Before
    fun setup() {
        marvelRepository = mock()
        connectionController = mock()
        charactersListViewModel = CharactersListViewModel(marvelRepository, connectionController)

        observer = mock()

        charactersListViewModel.isLoadingState.observeForever(observer)
    }

    @Test
    fun when_load_should_show_loading() {
        charactersListViewModel.getNewCharacters()
        verify(observer).onChanged(true)
    }
}