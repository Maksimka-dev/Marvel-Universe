package com.marvel.marveluniverse.model.json.characters

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(

    @Json(name = "results")
    val results: List<MarvelCharacter>
)
