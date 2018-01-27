package com.flatlander.flatlander.model.siteitem

/**
 * Created by iancowley on 7/31/17.
 */
class TextSiteItem(id: String,
                   type: String,
                   var title: String ?,
                   var description : String) : BaseSiteItem(id, type) {
}