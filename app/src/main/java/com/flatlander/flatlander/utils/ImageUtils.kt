package com.flatlander.flatlander.utils

import android.graphics.drawable.ColorDrawable
import android.support.annotation.ColorInt
import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Created by iancowley on 8/28/17.
 */
fun ImageView.loadImage(imageUrl: String, @ColorInt color: Int) {
    Picasso.with(this.context)
            .load(imageUrl)
            .placeholder(ColorDrawable(color))
            .into(this)
}