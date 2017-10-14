package com.flatlander.flatlander.model.siteitem

import java.io.Serializable

/**
 * Created by iancowley on 7/31/17.
 */
open class BaseSiteItem(var id : Int, var type : String) : Serializable {

    companion object {
        const val TYPE_MAP = "map"
        const val TYPE_IMAGE = "image"
        const val TYPE_TEXT = "text"
    }

}