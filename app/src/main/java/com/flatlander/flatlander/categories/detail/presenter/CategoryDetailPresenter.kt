package com.flatlander.flatlander.categories.detail.presenter

import android.graphics.Color
import android.util.Log
import com.flatlander.flatlander.R
import com.flatlander.flatlander.categories.detail.CategoryDetailContract
import com.flatlander.flatlander.model.Category
import com.flatlander.flatlander.model.SiteLite
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by iancowley on 8/24/17.
 */
class CategoryDetailPresenter(override val view: CategoryDetailContract.View,
                              val interactor: CategoryDetailContract.Interactor,
                              val category: Category) : CategoryDetailContract.Presenter {
    companion object {
        private val TAG = CategoryDetailPresenter::class.java.simpleName
        private const val ERROR_LOADING = 0
    }

    private val compositeDisposable = CompositeDisposable()
    private var isShowingMap = false

    override fun onViewAdded() {
        view.setHeaderColor(Color.parseColor(category.backgroundColor))
        loadSites()
    }

    override fun onViewRemoved() {
        compositeDisposable.dispose()
    }

    override fun onViewReturnedTo() {
        if (category.id == "favorites") {
            view.showToggleMapListFab(false)
            loadSites()
        }
    }

    override fun onSiteClicked(siteLite: SiteLite) {
        view.goToSiteScreen(category, siteLite)
    }

    override fun onToggleMapListFabClicked() {
        isShowingMap = !isShowingMap

        view.showMap(isShowingMap)
        view.showSiteList(!isShowingMap)
    }

    private fun loadSites() {
        view.showProgress(R.string.loading)
        compositeDisposable.add(interactor.getSitesForCategory(category)
                .doOnSuccess { view.hideProgress() }
                .doOnError { view.hideProgress() }
                .subscribe({
                    view.setSites(it)
                    view.showFavoritesEmptyState(category.id == "favorites" && it.isEmpty())
                    view.showToggleMapListFab(!it.isEmpty())
                }, {
                    Log.d(TAG, it.message)
                    view.showError(ERROR_LOADING, R.string.categories_error_load)
                }))
    }

    override fun onErrorDismissed(id: Int) {
    }

    override fun onMessagePositive(id: Int) {
    }

    override fun onMessageNegative(id: Int) {
    }
}