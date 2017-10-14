package com.flatlander.flatlander.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Created by iancowley on 8/28/17.
 */
fun ImageView.loadImage(imageUrl: String) {
    Picasso.with(this.context).load(imageUrl).into(this)
}