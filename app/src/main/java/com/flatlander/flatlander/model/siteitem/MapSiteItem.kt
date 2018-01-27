package com.flatlander.flatlander.model.siteitem

/**
 * Created by iancowley on 7/31/17.
 */
class MapSiteItem(id: String,
                  type: String,
                  var lat: String,
                  var long: String,
                  var name: String,
                  var googlePlaceId: String) : BaseSiteItem(id, type) {
    constructor() : this("", "", "0.0", "0.0", "", "")
}