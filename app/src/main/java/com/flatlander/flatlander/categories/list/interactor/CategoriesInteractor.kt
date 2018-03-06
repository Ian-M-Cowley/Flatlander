package com.flatlander.flatlander.categories.list.interactor

import com.flatlander.flatlander.categories.list.CategoriesContract
import com.flatlander.flatlander.data.CategoriesRepository
import com.flatlander.flatlander.data.FirebaseCategoriesRepository
import com.flatlander.flatlander.data.FirebaseSettingsRepository
import com.flatlander.flatlander.data.SettingsRepository
import com.flatlander.flatlander.model.Category
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by iancowley on 8/10/17.
 */
class CategoriesInteractor : CategoriesContract.Interactor {

    private val dataSource : CategoriesRepository = FirebaseCategoriesRepository.instance
    private val settingsDataSource : SettingsRepository = FirebaseSettingsRepository.instance

    override fun getCategories(): Single<List<Category>> {
        return dataSource.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMinimumVersion(): Single<Long> {
        return settingsDataSource.getMinimumVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}