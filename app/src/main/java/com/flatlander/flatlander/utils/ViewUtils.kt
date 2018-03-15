package com.flatlander.flatlander.utils

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

/**
 * Created by iancowley on 3/14/18.
 */
fun View.show(show: Boolean) {
    this.visibility = if (show) VISIBLE else GONE
}