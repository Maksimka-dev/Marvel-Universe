package com.marvel.marveluniverse.model.json.comics

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class URLS(

    @Json(name = "url")
    val url: String
)
