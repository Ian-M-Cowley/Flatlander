package com.flatlander.flatlander.model

import com.flatlander.flatlander.model.siteitem.MapSiteItem
import java.io.Serializable

/**
 * Created by iancowley on 8/24/17.
 */
open class SiteLite : Serializable {
    var id: String = ""
    var category: String = ""
    var name: String = ""
    var imageUrl: String = ""
    var title: String = ""
    var description: String = ""
    var defaultMapSiteItem: MapSiteItem? = null
    var distance = -1

    fun getUniqueId(): String {
        return category + id
    }

    override fun equals(other: Any?): Boolean {
        if (other is SiteLite) {
            return getUniqueId() == other.getUniqueId()
        }
        return false
    }
}