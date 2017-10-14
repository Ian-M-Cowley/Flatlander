package com.flatlander.flatlander.model

import java.io.Serializable

/**
 * Created by iancowley on 8/24/17.
 */
open class SiteLite : Serializable {
    var id: String = ""
    var name: String = ""
    var imageUrl: String = ""
    var title: String = ""
    var description: String = ""
}