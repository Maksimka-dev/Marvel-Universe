package com.marvel.marveluniverse.network

import com.marvel.marveluniverse.model.json.characters.CharactersApi
import com.marvel.marveluniverse.model.json.comics.ComicsApi
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("v1/public/characters?ts=1&apikey=7510189a53785a84a4671a4ec475f30c&hash=3d369c65bd275e39c94a250e75b9e2ae")
    suspend fun getNewPortionCharactersApi(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): CharactersApi?

    @GET("v1/public/comics?ts=1&apikey=7510189a53785a84a4671a4ec475f30c&hash=3d369c65bd275e39c94a250e75b9e2ae")
    suspend fun getNewComics(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): ComicsApi?
}
