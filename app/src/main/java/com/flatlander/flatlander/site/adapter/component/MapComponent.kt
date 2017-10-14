package com.flatlander.flatlander.site.adapter.component

import android.view.View
import butterknife.ButterKnife
import com.flatlander.flatlander.model.siteitem.BaseSiteItem
import com.flatlander.flatlander.model.siteitem.MapSiteItem

/**
 * Created by iancowley on 9/13/17.
 */
class MapComponent(itemView: View?) : BaseComponent(itemView) {

    init {
        ButterKnife.bind(this, itemView!!)
    }

    override fun bind(siteItem: BaseSiteItem) {
        val mapSiteItem = siteItem as MapSiteItem
    }
}