package com.flatlander.flatlander.model.siteitem

/**
 * Created by iancowley on 7/31/17.
 */
class MapSiteItem(id: Int,
                  type: String,
                  var lat: Float,
                  var lng : Float,
                  var name: String,
                  var googlePlacesId: String) : BaseSiteItem(id, type) {
}