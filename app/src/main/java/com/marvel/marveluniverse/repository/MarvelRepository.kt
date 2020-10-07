package com.marvel.marveluniverse.repository

import com.marvel.marveluniverse.db.MarvelRoomDatabase
import com.marvel.marveluniverse.db.marvel.entity.CharacterEntity
import com.marvel.marveluniverse.db.marvel.entity.ComicsEntity
import com.marvel.marveluniverse.model.Character
import com.marvel.marveluniverse.model.Comics
import com.marvel.marveluniverse.network.MarvelApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarvelRepository @Inject constructor(
    private val marvelRoomDatabase: MarvelRoomDatabase,
    private val marvelApi: MarvelApi
) {
    private val characterDao = marvelRoomDatabase.characterDao()
    private val comicsDao = marvelRoomDatabase.comicsDao()

    suspend fun getNewCharacters(
        fromCharacterInRoom: Int,
        countOfCharacters: Int
    ) = withContext(Dispatchers.IO) {
        val listCharacters: List<Character>
        val list = characterDao.getNewCharacters(fromCharacterInRoom, countOfCharacters)

        listCharacters = if (list.isNullOrEmpty()) {
            loadCharactersFromNetwork(fromCharacterInRoom, countOfCharacters)
            characterDao.getAll().map { it.toCharacter() }
        } else {
            list.map { it.toCharacter() }
        }

        listCharacters
    }

    private suspend fun loadCharactersFromNetwork(
        fromCharacterInRoom: Int,
        countOfCharacters: Int
    ) {
        withContext(Dispatchers.IO) {
            val list = marvelApi
                .getNewPortionCharactersApi(fromCharacterInRoom, countOfCharacters)
                ?.data
                ?.results
                ?.map { marvelCharacter ->
                    with(marvelCharacter) {
                        Character(
                            id,
                            name,
                            description,
                            thumbnail.path + "." + thumbnail.extension
                        )
                    }
                }

            characterDao.insertAll(list?.map { CharacterEntity(it) })
        }
    }

    suspend fun getCharacterById(id: Int?) = withContext(Dispatchers.IO) {
        characterDao.getById(id).toCharacter()
    }

    suspend fun getNewComics(
        fromComicsInRoom: Int,
        countOfComics: Int
    ) = withContext(Dispatchers.IO) {
        var listComics: List<Comics>
        val list = comicsDao.getNewComics(fromComicsInRoom, countOfComics)

        listComics = if (list.isNullOrEmpty()) {
            loadComicsFromNetwork(fromComicsInRoom, countOfComics)
            comicsDao.getAll().map { it.toComics() }
        } else {
            list.map { it.toComics() }
        }

        listComics
    }

    suspend fun getCharacterByName(count: Int): List<Character>? {
        return withContext(Dispatchers.IO) {

            val characters = characterDao
                .getNewCharacters(count, 100)
                ?.map { it.toCharacter() }

            characters
        }
    }

    private suspend fun loadComicsFromNetwork(fromComicsInRoom: Int, countOfComics: Int) {
        withContext(Dispatchers.IO) {
            val list = marvelApi
                .getNewComics(fromComicsInRoom, countOfComics)
                ?.data
                ?.results
                ?.map { marvelComics ->
                    with(marvelComics) {
                        val id = id
                        val title = title
                        val description = description ?: ""
                        val thumbnail = thumbnail.path + "." + thumbnail.extension
                        val url = urls[0].url

                        Comics(id, title, description, thumbnail, url)
                    }
                }
            comicsDao.insertAll(list?.map { ComicsEntity(it) })
        }
    }

    suspend fun getComicsById(id: Int?) = withContext(Dispatchers.IO) {
        comicsDao.getById(id).toComics()
    }

    companion object {
        private val TAG = MarvelRepository::class.java.simpleName
    }
}
