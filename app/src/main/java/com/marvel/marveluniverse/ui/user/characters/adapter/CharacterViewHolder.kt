package com.marvel.marveluniverse.ui.user.characters.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marvel.marveluniverse.R
import com.marvel.marveluniverse.model.Character

class CharacterViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val characterThumbnail: ImageView = view.findViewById(R.id.character_thumbnail)
    private val characterName: TextView = view.findViewById(R.id.character_name)


    fun bind(character: Character) {
        Glide
            .with(characterThumbnail.context)
            .load(character.thumbnail)
            .placeholder(R.drawable.ic_baseline_disconnect_24)
            .into(characterThumbnail)


        characterName.text = character.name
    }
}
