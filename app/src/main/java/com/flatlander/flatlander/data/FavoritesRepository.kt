package com.flatlander.flatlander.data

import com.flatlander.flatlander.model.Site
import com.flatlander.flatlander.model.SiteLite
import io.reactivex.Single

/**
 * Created by iancowley on 1/28/18.
 */
interface FavoritesRepository {

    fun favoriteSite(site: Site): Single<Boolean>
    fun unfavoriteSite(site: Site): Single<Boolean>
    fun isSiteFavorite(id: String): Boolean
    fun getFavoriteSites(): Single<List<SiteLite>>
}