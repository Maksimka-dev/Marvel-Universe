package com.marvel.marveluniverse.model.json.comics

import com.marvel.marveluniverse.model.json.characters.Thumbnail
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarvelComics(

    @Json(name = "id")
    val id: Int?,

    @Json(name = "title")
    val title: String,

    @Json(name = "description")
    val description: String?,

    @Json(name = "thumbnail")
    val thumbnail: Thumbnail,

    @Json(name = "urls")
    val urls: List<URLS>
)
