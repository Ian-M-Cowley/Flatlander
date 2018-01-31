package com.flatlander.flatlander.site.presenter

import android.util.Log
import com.flatlander.flatlander.R
import com.flatlander.flatlander.model.Site
import com.flatlander.flatlander.model.SiteLite
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

    override fun onViewAdded() {
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

                    // Add map site item
                    site.defaultMapSiteItem?.let {
                        site.siteItems.add(0, it)
                    }

                    // Add the header title and description
                    site.siteItems.add(0, TextSiteItem("-1", "text", site.title, site.description))

                    view.setSiteItems(site.siteItems)
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