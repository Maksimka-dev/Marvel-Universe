package com.marvel.marveluniverse.db.marvel.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marvel.marveluniverse.model.Comics

@Entity(tableName = "comics_table")
data class ComicsEntity(

    @PrimaryKey(autoGenerate = true)
    val comicsId: Int?,

    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "thumbnail")
    val thumbnail: String,

    @ColumnInfo(name = "url")
    val url: String?
) {
    constructor(comics: Comics): this(
        null,
        comics.id,
        comics.title,
        comics.description,
        comics.thumbnail,
        comics.url
    )

    fun toComics() = Comics(id, title, description, thumbnail, url)
}
