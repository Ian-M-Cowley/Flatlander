package com.flatlander.flatlander.model.siteitem

/**
 * Created by iancowley on 7/31/17.
 */
class ImageSiteItem(id: Int,
                    type: String,
                    var imageUrl : String,
                    var width: Float,
                    var height : Float) : BaseSiteItem(id, type) {
}