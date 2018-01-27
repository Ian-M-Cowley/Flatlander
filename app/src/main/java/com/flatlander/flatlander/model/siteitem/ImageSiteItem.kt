package com.flatlander.flatlander.model.siteitem

/**
 * Created by iancowley on 7/31/17.
 */
class ImageSiteItem(id: String,
                    type: String,
                    var imageUrl : String,
                    var width: String,
                    var height : String) : BaseSiteItem(id, type) {
}