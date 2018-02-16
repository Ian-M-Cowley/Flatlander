package com.flatlander.flatlander.data

import com.flatlander.flatlander.model.SiteLite
import io.reactivex.Single

/**
 * Created by iancowley on 1/28/18.
 */
interface FavoritesRepository {

    fun favoriteSite(siteLite: SiteLite): Single<Boolean>
    fun unfavoriteSite(siteLite: SiteLite): Single<Boolean>
    fun isSiteFavorite(siteLite: SiteLite): Boolean
    fun getFavoriteSites(): Single<List<SiteLite>>
}