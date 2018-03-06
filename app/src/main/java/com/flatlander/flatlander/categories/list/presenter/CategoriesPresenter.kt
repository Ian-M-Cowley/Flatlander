package com.flatlander.flatlander.categories.list.presenter

import com.flatlander.flatlander.BuildConfig
import com.flatlander.flatlander.R
import com.flatlander.flatlander.categories.list.CategoriesContract
import com.flatlander.flatlander.model.Category
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by iancowley on 8/10/17.
 */
class CategoriesPresenter(override val view: CategoriesContract.View,
                          val interactor: CategoriesContract.Interactor) : CategoriesContract.Presenter {

    companion object {
        private const val ERROR_LOADING = 0
        private const val ERROR_NEED_UPGRADE = 1
    }

    val compositeDisposable = CompositeDisposable()

    override fun onViewAdded() {
        view.showProgress(R.string.loading)
        compositeDisposable.add(interactor.getCategories()
                .doOnSuccess { view.hideProgress() }
                .doOnError { view.hideProgress() }
                .subscribe({
                    view.setCategories(it)
                }, {
                    view.showError(ERROR_LOADING, R.string.categories_error_load)
                }))

        compositeDisposable.add(interactor.getMinimumVersion()
                .subscribe({
                    if (it > BuildConfig.VERSION_CODE) {
                        view.showError(ERROR_NEED_UPGRADE, R.string.categories_error_need_upgrade)
                    }
                }, {
                }))

    }

    override fun onViewRemoved() {
        compositeDisposable.dispose()
    }


    override fun onCategoryClicked(category: Category) {
        view.goToCategoryDetailScreen(category)
    }

    override fun onErrorDismissed(id: Int) {
        if (id == ERROR_NEED_UPGRADE) {
            view.close()
        }
    }

    override fun onMessagePositive(id: Int) {
    }

    override fun onMessageNegative(id: Int) {
    }
}