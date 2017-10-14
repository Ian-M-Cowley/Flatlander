package com.flatlander.flatlander.site.presenter

import com.flatlander.flatlander.R
import com.flatlander.flatlander.model.SiteLite
import com.flatlander.flatlander.site.SiteContract
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

    override fun onViewAdded() {
        view.setSiteName(siteLite.name)
        view.loadSiteImage(siteLite.imageUrl)

        view.showProgress(R.string.loading)
        compositeDisposable.add(interactor.getSite(siteLite.id)
                .doOnSuccess { view.hideProgress() }
                .doOnError { view.hideProgress() }
                .subscribe({
                    view.setHeaderTitle(it.title)
                    view.setHeaderDescription(it.description)
                    view.setSiteItems(it.siteItems)
                }, {
                    view.showError(ERROR_LOADING, R.string.categories_error_load)
                }))
    }

    override fun onViewRemoved() {
        compositeDisposable.dispose()
    }

    override fun onErrorDismissed(id: Int) {
    }

    override fun onMessagePositive(id: Int) {
    }

    override fun onMessageNegative(id: Int) {
    }
}