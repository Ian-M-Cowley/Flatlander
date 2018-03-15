package com.flatlander.flatlander.model

import java.io.Serializable

/**
 * Created by iancowley on 6/14/17.
 */
data class Category(var id: String,
                    var name: String,
                    var imageId: Int,
                    var backgroundColor: String) : Serializable