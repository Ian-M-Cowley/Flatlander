package com.flatlander.flatlander.model

import com.flatlander.flatlander.model.siteitem.BaseSiteItem
import com.flatlander.flatlander.model.siteitem.MapSiteItem
import java.io.Serializable

/**
 * Created by iancowley on 7/31/17.
 */
class Site : SiteLite(), Serializable {
    var defaultMapSiteItem: MapSiteItem? = null
    var siteItems: MutableList<BaseSiteItem> = mutableListOf()
}
