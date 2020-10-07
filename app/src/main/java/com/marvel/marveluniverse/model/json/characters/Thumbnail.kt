package com.marvel.marveluniverse.model.json.characters

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Thumbnail(

    @Json(name = "path")
    val path: String,

    @Json(name = "extension")
    val extension: String
)
