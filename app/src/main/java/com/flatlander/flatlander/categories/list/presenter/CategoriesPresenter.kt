package com.flatlander.flatlander.categories.list.presenter

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

    }

    override fun onViewRemoved() {
        compositeDisposable.dispose()
    }


    override fun onCategoryClicked(category: Category) {
        view.goToCategoryDetailScreen(category)
    }

    override fun onErrorDismissed(id: Int) {
    }

    override fun onMessagePositive(id: Int) {
    }

    override fun onMessageNegative(id: Int) {
    }
}