package com.marvel.marveluniverse.ui.user.comics.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marvel.marveluniverse.R
import com.marvel.marveluniverse.model.Comics

class ComicsViewHolder(val context: Context, val view: View): RecyclerView.ViewHolder(view) {

    private val comicsThumbnail: ImageView = view.findViewById(R.id.comics_thumbnail)
    private val comicsTitle: TextView = view.findViewById(R.id.comics_title)

    fun bind(comics: Comics) {

        Glide
            .with(context)
            .load(comics.thumbnail)
            .placeholder(R.drawable.ic_baseline_disconnect_24)
            .into(comicsThumbnail)

        comicsTitle.text = comics.title
    }
}
