package com.marvel.marveluniverse.db.marvel.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marvel.marveluniverse.model.Character

@Entity(tableName = "characters_table")
data class CharacterEntity(

    @PrimaryKey(autoGenerate = true)
    val characterId: Int?,

    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "thumbnail")
    val thumbnail: String
) {
    constructor(character: Character) : this(
        null,
        character.id,
        character.name,
        character.description,
        character.thumbnail
    )

    fun toCharacter() = Character(id, name, description, thumbnail)
}
