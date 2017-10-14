package com.flatlander.flatlander.site.adapter.component

import android.support.v7.widget.RecyclerView
import android.view.View
import com.flatlander.flatlander.model.siteitem.BaseSiteItem

/**
 * Created by iancowley on 9/13/17.
 */
open abstract class BaseComponent(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(siteItem: BaseSiteItem)
}