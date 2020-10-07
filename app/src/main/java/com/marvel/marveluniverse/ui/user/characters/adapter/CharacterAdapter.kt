package com.marvel.marveluniverse.ui.user.characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.marvel.marveluniverse.R
import com.marvel.marveluniverse.model.Character


class CharacterAdapter(
    private val onCharacterSelected: (character: Character) -> Unit
) : ListAdapter<Character, CharacterViewHolder>(
    characterDiffUtilCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)

        holder.itemView.setOnClickListener {
            onCharacterSelected(character)
        }

        holder.bind(character)
    }

    companion object {

        private val characterDiffUtilCallback = object : DiffUtil.ItemCallback<Character>() {

            override fun areItemsTheSame(
                oldCharacter: Character,
                newCharacter: Character
            ): Boolean {
                return oldCharacter.id == newCharacter.id
            }

            override fun areContentsTheSame(
                oldCharacter: Character,
                newCharacter: Character
            ): Boolean {
                return oldCharacter == newCharacter
            }

        }
    }
}
