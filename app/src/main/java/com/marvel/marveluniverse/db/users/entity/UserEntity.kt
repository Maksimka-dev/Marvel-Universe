package com.marvel.marveluniverse.db.users.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marvel.marveluniverse.model.User

@Entity(tableName = "user_table")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long?,

    @ColumnInfo(name = "username")
    val userName: String,

    @ColumnInfo(name = "password")
    val password: String
) {
    constructor(user: User) : this(
        null,
        user.userName,
        user.password
    )

    fun toUser() = User(id, userName, password)
}
