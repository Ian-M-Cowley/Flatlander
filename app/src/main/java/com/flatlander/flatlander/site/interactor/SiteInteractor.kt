package com.flatlander.flatlander.site.interactor

import com.flatlander.flatlander.data.FavoritesRepository
import com.flatlander.flatlander.data.FirebaseSitesRepository
import com.flatlander.flatlander.data.LocalFavoritesRepository
import com.flatlander.flatlander.data.SitesRepository
import com.flatlander.flatlander.model.Site
import com.flatlander.flatlander.site.SiteContract
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by iancowley on 8/28/17.
 */
class SiteInteractor : SiteContract.Interactor {

    private val dataSource : SitesRepository = FirebaseSitesRepository.instance
    private val favoritesDataSource: FavoritesRepository = LocalFavoritesRepository.instance

    override fun getSite(siteId: String) : Single<Site> {
        return dataSource.getSiteById(siteId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun isSiteFavorite(site: Site): Boolean {
        return favoritesDataSource.isSiteFavorite(site.id)
    }

    override fun favoriteSite(site: Site): Single<Boolean> {
        return favoritesDataSource.favoriteSite(site)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun unfavoriteSite(site: Site): Single<Boolean> {
        return favoritesDataSource.unfavoriteSite(site)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}