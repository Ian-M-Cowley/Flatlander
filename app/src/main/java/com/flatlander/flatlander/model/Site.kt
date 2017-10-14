package com.flatlander.flatlander.model

import com.flatlander.flatlander.model.siteitem.BaseSiteItem
import java.io.Serializable

/**
 * Created by iancowley on 7/31/17.
 */
class Site : SiteLite(), Serializable {
    var siteItems: List<BaseSiteItem> = emptyList()
}
