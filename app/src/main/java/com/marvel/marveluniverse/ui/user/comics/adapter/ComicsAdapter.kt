package com.marvel.marveluniverse.ui.user.comics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.marvel.marveluniverse.R
import com.marvel.marveluniverse.model.Comics

class ComicsAdapter(
    private val onComicsSelected: (comics: Comics) -> Unit
) : ListAdapter<Comics, ComicsViewHolder>(
    comicsDiffUtilCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comics_item, parent, false)
        return ComicsViewHolder(parent.context, view)
    }

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) {
        val comics = getItem(position)

        holder.itemView.setOnClickListener {
            onComicsSelected(comics)
        }
        holder.bind(comics)
    }

    companion object {

        private val comicsDiffUtilCallback = object : DiffUtil.ItemCallback<Comics>() {

            override fun areItemsTheSame(
                oldComics: Comics,
                newComics: Comics
            ): Boolean {
                return oldComics.id == newComics.id
            }

            override fun areContentsTheSame(
                oldComics: Comics,
                newComics: Comics
            ): Boolean {
                return oldComics == newComics
            }

        }
    }
}
