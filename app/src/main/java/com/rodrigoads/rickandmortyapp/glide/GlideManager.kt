package com.rodrigoads.rickandmortyapp.glide

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.rodrigoads.rickandmortyapp.R

class GlideManager {
    fun setImage(imageView: ImageView, url: String) {
        Glide.with(imageView)
            .load(url)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(imageView)
    }
}