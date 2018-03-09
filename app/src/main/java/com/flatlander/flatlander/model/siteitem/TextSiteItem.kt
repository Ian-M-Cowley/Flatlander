package com.flatlander.flatlander.model.siteitem

/**
 * Created by iancowley on 7/31/17.
 */
class TextSiteItem(id: String,
                   type: String,
                   hikingRank: Long,
                   campingRank: Long,
                   swimmingRank: Long,
                   var title: String?,
                   var description: String) : BaseSiteItem(id, type, hikingRank, campingRank, swimmingRank) {
}