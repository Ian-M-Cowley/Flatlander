package com.flatlander.flatlander.site.presenter

import android.util.Log
import com.flatlander.flatlander.R
import com.flatlander.flatlander.model.Site
import com.flatlander.flatlander.model.SiteLite
import com.flatlander.flatlander.model.siteitem.BaseSiteItem
import com.flatlander.flatlander.model.siteitem.MapSiteItem
import com.flatlander.flatlander.model.siteitem.TextSiteItem
import com.flatlander.flatlander.site.SiteContract
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by iancowley on 8/28/17.
 */
class SitePresenter(override val view: SiteContract.View,
                    val interactor: SiteContract.Interactor,
                    val siteLite: SiteLite) : SiteContract.Presenter {

    companion object {
        private const val ERROR_LOADING = 0
    }

    private val compositeDisposable = CompositeDisposable()
    private var isFavorite = false
    private lateinit var site: Site
    private val siteItems: MutableList<BaseSiteItem> = mutableListOf()

    override fun onViewAdded() {
        // Add the header title and description
        siteItems.add( TextSiteItem("-1", "text", siteLite.title, siteLite.description))
        siteLite.defaultMapSiteItem.let { siteItems.add(it!!) }
        view.setSiteItems(siteItems)

        view.setSiteName(siteLite.name)
        view.loadSiteImage(siteLite.imageUrl)

        view.showProgress(R.string.loading)
        compositeDisposable.add(interactor.getSite(siteLite.id)
                .doOnSuccess { view.hideProgress() }
                .doOnError { view.hideProgress() }
                .subscribe({
                    site = it

                    isFavorite = interactor.isSiteFavorite(site)
                    view.setFavorite(isFavorite)

                    siteItems.addAll(site.siteItems)
                    view.notifySiteItemsChanged()
                }, {
                    Log.d("SitePresenter", it.message)
                    view.showError(ERROR_LOADING, R.string.categories_error_load)
                }))
    }

    override fun onViewRemoved() {
        compositeDisposable.dispose()
    }

    override fun onFavoriteClicked() {
        view.showProgress(R.string.loading)
        val action: Single<Boolean> = if (isFavorite) {
            interactor.unfavoriteSite(site)
        } else {
            interactor.favoriteSite(site)
        }
        compositeDisposable.add(action
                .doOnSuccess { view.hideProgress() }
                .doOnError { view.hideProgress() }
                .subscribe({
                    if (it) {
                        isFavorite = !isFavorite
                        view.setFavorite(isFavorite)
                        if (isFavorite) {
                            view.showSnackbar(R.string.sites_favorite)
                        } else {
                            view.showSnackbar(R.string.sites_unfavorite)
                        }
                    }
                }, {
                    if (!isFavorite) {
                        view.showSnackbar(R.string.sites_error_favorite)
                    } else {
                        view.showSnackbar(R.string.sites_error_unfavorite)
                    }
                }))
    }

    override fun onMapItemSelected(mapSiteItem: MapSiteItem) {
        view.goToMapScreen(mapSiteItem)
    }

    override fun onErrorDismissed(id: Int) {
    }

    override fun onMessagePositive(id: Int) {
    }

    override fun onMessageNegative(id: Int) {
    }
}