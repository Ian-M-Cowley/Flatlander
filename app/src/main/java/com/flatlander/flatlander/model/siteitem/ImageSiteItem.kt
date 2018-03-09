package com.flatlander.flatlander.model.siteitem

/**
 * Created by iancowley on 7/31/17.
 */
class ImageSiteItem(id: String,
                    type: String,
                    hikingRank: Long,
                    campingRank: Long,
                    swimmingRank: Long,
                    var imageUrl : String,
                    var width: String,
                    var height : String) : BaseSiteItem(id, type, hikingRank, campingRank, swimmingRank) {
}