package com.flatlander.flatlander.model.siteitem

/**
 * Created by iancowley on 7/31/17.
 */
class MapSiteItem(id: String,
                  type: String,
                  hikingRank: Long,
                  campingRank: Long,
                  swimmingRank: Long,
                  var lat: String,
                  var long: String,
                  var name: String,
                  var googlePlaceId: String) : BaseSiteItem(id, type, hikingRank, campingRank, swimmingRank) {
    constructor() : this("", "", -1, -1, -1, "0.0", "0.0", "", "")
}