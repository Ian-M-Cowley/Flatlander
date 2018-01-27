package com.flatlander.flatlander.data

import com.flatlander.flatlander.model.Site
import io.reactivex.Single

/**
 * Created by iancowley on 8/24/17.
 */
interface SitesRepository {

    fun getSiteById(id: String) : Single<Site>
}